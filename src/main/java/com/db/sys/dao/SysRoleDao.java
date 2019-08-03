package com.db.sys.dao;

import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleDao {
    /**
     * 查询角色信息
     * @param name
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SysRole> findPageObjects(
            @Param("name")String name,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);
    /**
     * 查询记录总数
     * @return
     */
    int getRowCount(@Param("name")String name);

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    int deleteObject(Integer id);

    /**
     * 添加角色时,保存角色信息
     * @param entity
     * @return
     */
    int insertObject(SysRole entity);

    /**
     * 修改角色时,先根据id查询该角色
     * 1.在mapper中查找SysroleMenuVo
     * 2.SysRoleMenuVo中去角色,菜单的关系表中查找集合menuIds
     * @param id
     * @return
     */
    SysRoleMenuVo findObjectById(Integer id);
}
