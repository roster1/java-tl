package com.db.sys.service;

import com.db.sys.entity.SysRole;
import com.db.sys.vo.PageObject;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
    /**
     * 根据id删除数据
     * 根据角色id删除角色,菜单关系数据
     * 根据角色id删除角色,用户关系数据
     * @param id
     * @return
     */
//    int deleteObject(Integer id);

    /**
     * 本方法中要分页查询角色信息,并查询角色总记录数据
     * @param pageCurrent 当表要查询的当前页的页码值
     * @return 封装当前实体数据以及分页信息
     */
    PageObject<SysRole> findPageObjects(
            String name,Integer pageCurrent);

    /**
     * 删除角色信息
     * @param id
     * @return
     */
    int deleteObject(Integer id);

    /**
     * 添加角色
     * @param entity
     * @return
     */
    int saveObject(SysRole entity,Integer[] menuIds);

    /**
     * 修改角色时,根据id查询角色信息
     * @param id
     * @return
     */
    SysRoleMenuVo findObjectById(Integer id);
}
