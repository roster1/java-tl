package com.db.sys.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleDao {
    /**
     *删除角色时,根据角色id删除角色,用户的关系数据
     * @param roleId
     * @return
     */
    int deleteObjectsByRoleId(Integer roleId);

    /*更新角色时,要更具角色id删除角色,菜单的关系数据*/
    int deleteObjectsByUserId(Integer userId);

    /*基于用户id保存用户,角色的关系数据 insertObjects*/
    int insertByUserId(@Param("userId") Integer userId,
                       @Param("roleIds") Integer[] roleIds);

    /*------------*/
    /*修改用户时,将用户,角色的关系数据回传
    *
    * 授权实现,基于用户id查询角色id
    * */
    List<Integer> findRoleIdsByUserId(Integer userId);

}
