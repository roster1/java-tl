package com.db.sys.dao;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
    /**
     * 根据角色id删除角色,用户的关系数据
     * @param roleId
     * @return
     */
    int deleteObjectsByRoleId(Integer roleId);

    /*基于用户id保存用户,角色的关系数据*/
    int insertByUserId(@Param("userId") Integer userId,
                       @Param("roleIds") Integer[] roleIds);
}
