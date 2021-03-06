package com.db.sys.service.impl;

import com.db.common.annotation.RequiedLog;
import com.db.common.exception.ServiceException;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.PageObject;
import com.db.sys.vo.SysUserDeptVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        //4.基于条件查询分页数据
        List<SysUserDeptVo> records = sysUserDao.findPageObjects(name,startIndex,pageSize);
        //5.封装数据PageObject,并返回
        PageObject<SysUserDeptVo> pageObject = new PageObject<>();
        pageObject.setPageCount((rows-1)/pageSize+1);
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
        pageObject.setRowCount(rows);
        return pageObject;
    }

    /**
     * 实现用户的禁用启用
     * @param id
     * @param valid
     * @param modifiedUser
     * @return
     */
    @RequiedLog("禁用启用")
    @RequiresPermissions("sys:user:valid")
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

    /*添加用户之前,将用户要选择的角色显示,供用户选择*/

    /**
     * 保存新添加的用户
     * @param entity
     * @param roleIds
     * @return
     */
    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        //1.验证参数的合法性
        if(entity == null)
            throw new IllegalArgumentException("对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        if(StringUtils.isEmpty(entity.getPassword()))
            throw new ServiceException("用户的密码不能为空");
        if(roleIds == null || roleIds.length == 0)
            throw new ServiceException("必须为用户分配角色");
        //2.保存数据
        //2.1保存用户的数据
        String salt = UUID.randomUUID().toString();
        entity.setSalt(salt);
        SimpleHash sHash = new SimpleHash("MD5", entity.getPassword(), salt);
        entity.setPassword(sHash.toHex());
        int row = sysUserDao.insertObject(entity);
        if(row == 0)
            throw new ServiceException("保存失败");
        //2.2基于用户id保存用户,角色的关系数据
        int count = 0;
        try {
            count = sysUserRoleDao.insertByUserId(entity.getId(),roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("系统维护中...");
        }
        if(count <=0)
            throw new ServiceException("保存失败");
        return row;
    }

    /**
     * 修改用户
     * 1.回显要修改的用户
     *
     * 2.修改的用户入库
     * @param id
     * @return
     */
    @Override
    public Map<String,Object> findObjectById(Integer id) {
        if(id == null || id<=0)
            throw new IllegalArgumentException("传入的id无效,id:"+id);
        //查询数据
        SysUserDeptVo user = sysUserDao.findObjectById(id);
        if(user == null)
            throw new ServiceException("该用户不存在");
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
        //封装数据并返回
        Map<String, Object> map = new HashMap<>();
        map.put("user",user);
        map.put("roleIds",roleIds);
        return map;
    }
    /**2.将修改的用户数据入库*/
    @Override
    public int updateObject(SysUser entity, Integer[] roleIds) {
        if(entity == null)
            throw new IllegalArgumentException("对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new ServiceException("用户名不能为空");
        int row = sysUserDao.updateObject(entity);
        if(roleIds.length == 0)
            throw new ServiceException("必须为用户分配角色");
        //修改用户时先删除用户和角色的关系
        sysUserRoleDao.deleteObjectsByUserId(entity.getId());
        sysUserRoleDao.insertByUserId(entity.getId(),roleIds);
        return row ;
    }
}
