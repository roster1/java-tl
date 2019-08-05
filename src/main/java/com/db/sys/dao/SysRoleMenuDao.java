package com.db.sys.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuDao {
	/**
	 * 根据菜单id删除菜单和角色的关系数据
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);

	/*删除角色时,通过角色id删除,角色,菜单的关系数据*/
	int deleteObjectsByRoleId(Integer RoleId);

	/**
	 * 添加角色时,保存角色和菜单的关系
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(@Param("roleId") int roleId,@Param("menuIds")Integer[] menuIds);

	/*授权实现,基于角色id获取菜单id*/
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer[] roleIds);
}
