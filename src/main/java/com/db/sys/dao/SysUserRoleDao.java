package com.db.sys.dao;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
    /**
     *删除角色时,根据角色id删除角色,用户的关系数据
     *
     * 更新角色时,要更具角色id删除角色,菜单的关系数据
     * @param roleId
     * @return
     */
    int deleteObjectsByRoleId(Integer roleId);

    /*基于用户id保存用户,角色的关系数据*/
    int insertByUserId(@Param("userId") Integer userId,
                       @Param("roleIds") Integer[] roleIds);

}
