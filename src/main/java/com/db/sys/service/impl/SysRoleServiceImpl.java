package com.db.sys.service.impl;

import com.db.common.exception.ServiceException;
import com.db.common.vo.CheckBox;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.PageObject;
import com.db.sys.vo.SysRoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 加载页面的分页信息
     * 返回封装好的页面对象
     * @return
     */
    @Override
    public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
        //1.验证参数合法性
        //1.1验证pageCurrent的合法性，
        //不合法抛出IllegalArgumentException异常
        if(pageCurrent==null||pageCurrent<1)
            throw new IllegalArgumentException("当前页码不正确");
        //2.基于条件查询总记录数
        //2.1) 执行查询
        int rowCount=sysRoleDao.getRowCount(name);
        //2.2) 验证查询结果，假如结果为0不再执行如下操作
        if(rowCount==0)
            throw new ServiceException("记录不存在");
        //3.基于条件查询当前页记录(pageSize定义为2)
        //3.1)定义pageSize
        int pageSize=2;
        //3.2)计算startIndex
        int startIndex=(pageCurrent-1)*pageSize;
        //3.3)执行当前数据的查询操作
        List<SysRole> records=
                sysRoleDao.findPageObjects(name, startIndex, pageSize);
        //4.对分页信息以及当前页记录进行封装
        //4.1)构建PageObject对象
        PageObject<SysRole> pageObject=new PageObject<>();
        //4.2)封装数据
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRowCount(rowCount);
        pageObject.setRecords(records);
        pageObject.setPageCount((rowCount-1)/pageSize+1);
        //5.返回封装结果。
        return pageObject;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public int deleteObject(Integer id) {
        //id=0;//必须大于0
        //1.判断参数是否有效
        if(id == null || id < 1)
            throw new IllegalArgumentException("参数无效,id:"+id);
        //2.根据id删除角色数据
        int row = sysRoleDao.deleteObjectsByRoleId(id);
        if(row == 0)
            throw new ServiceException("记录可能已经不存在");
        //3.根据角色id删除角色,菜单关系数据
        sysRoleMenuDao.deleteObjectsByRoleId(id);
        //4.根据角色id删除角色,用户关系数据
        sysUserRoleDao.deleteObjectsByRoleId(id);
        return row;
    }

    /**
     * 添加角色时,1.添加角色的信息
     *              2.添加角色,菜单的关系数据
     * @param entity
     * @return
     */
    @Override
    public int saveObject(SysRole entity,Integer[] menuIds) {
        //1.判断参数是否有效
        if(entity == null)
            throw new IllegalArgumentException("对象不能为空");
        if(StringUtils.isEmpty(entity.getName()== null))
            throw new ServiceException("对象名称不能为空");
        if(menuIds == null || menuIds.length == 0)
            throw new ServiceException("必须为角色赋予权限");
        //2.添加角色信息
        int row = sysRoleDao.insertObject(entity);
        //3.添加角色,菜单的关系数据
        System.out.println("id:"+entity.getId());
        sysRoleMenuDao.insertObjects(entity.getId(), menuIds);

        return row;
    }

    /**
     * 修改角色时,先根据id查询角色信息
     * 查询的结果
     * @param id
     * @return
     */
    @Override
    public SysRoleMenuVo findObjectById(Integer id) {
        //1.判断参数的合法性
        if(id == null || id<=0)
            throw new IllegalArgumentException("参数有误,id:"+id);
        //2.查询角色信息
        SysRoleMenuVo vo = sysRoleDao.findObjectById(id);
        if(vo == null)
            throw new ServiceException("查询记录不存在");
        //3.返回查询结果
        return vo;
    }
    /*将修改的角色信息保存*/
    @Override
    public int updateObject(SysRole entity, Integer[] menuIds) {
        //1.验证参数的合法性
        if(entity == null)
            throw new IllegalArgumentException("对象不能为空");
        if(entity.getId()<=0)
            throw new IllegalArgumentException("对象的id不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("对象名不能为空");
        if(menuIds == null || menuIds.length == 0)
            throw new ServiceException("必须为角色分配权限");
        int row = sysRoleDao.updateObject(entity);
        if(row == 0)
            throw new ServiceException("该记录可能已经不存在");
        sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
        sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
        return row;
    }

    /*-------*/
    /**添加用户时,提供可选择的角色*/
    @Override
    public List<CheckBox> findObjects() {
        List<CheckBox> checkBoxes = sysRoleDao.findObjects();
        return checkBoxes;
    }

}
