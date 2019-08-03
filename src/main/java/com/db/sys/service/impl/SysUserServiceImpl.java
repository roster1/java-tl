package com.db.sys.service.impl;

import com.db.common.exception.ServiceException;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.PageObject;
import com.db.sys.vo.SysUserDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public PageObject<SysUserDeptVo> findPageObjects(String name, Integer pageCurrent) {
        //1.判断参数是否有效
        if(pageCurrent == null || pageCurrent<=0)
            throw new IllegalArgumentException("参数有误,pageCurrent:"+pageCurrent);
        //2.查询有多少条记录,后面进行分页查询
        int rows = sysUserDao.getRowCount(name);
        //3.计算startIndex
        int pageSize = 4;
        int startIndex = (pageCurrent-1)*pageSize;
        //计算总页数
        int pageCount = rows;
        //4.基于条件查询分页数据
        List<SysUserDeptVo> records = sysUserDao.findPageObjects(name,startIndex,pageSize);
        //5.封装数据PageObject,并返回
        PageObject<SysUserDeptVo> pageObject = new PageObject<>();
        pageObject.setPageCount((rows-1)/(pageSize+1));
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
        return pageObject;
    }

    /**
     * 实现用户的禁用启用
     * @param id
     * @param valid
     * @param modifiedUser
     * @return
     */
    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {
        if(id == null || id<=0)
            throw new IllegalArgumentException("参数有误,id:"+id);
        if(valid != 0 && valid !=1)
            throw new IllegalArgumentException("参数有误,valid:"+valid);
        if(StringUtils.isEmpty(modifiedUser)){
            throw new IllegalArgumentException("修改的用户不能为空");
        }
        int row=0;
        try {
            row =sysUserDao.validById(id,valid,modifiedUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("底层正在维护");
        }
        if(row ==0)
            throw new ServiceException("该记录可能已经不存在");
        return row;
    }

    /**
     * 保存新添加的用户
     * @param sysUser
     * @param roleIds
     * @return
     */
    @Override
    public int saveObject(SysUser sysUser, Integer[] roleIds) {
        //1.验证参数的合法性
        if(sysUser == null)
            throw new IllegalArgumentException("对象不能为空");
        if(StringUtils.isEmpty(sysUser.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        //2.保存数据
        //2.1保存用户的数据
        int row = sysUserDao.insertObject(sysUser);
        if(row == 0)
            throw new ServiceException("保存失败");
        //2.2基于用户id保存用户,角色的关系数据
        int count = sysUserRoleDao.insertByUserId(sysUser.getId(),roleIds);
        if(count <=0)
            throw new ServiceException("保存失败");
        return row;
    }
}