package com.db.sys.dao;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
	/**
	 * 根据菜单id删除菜单和角色的关系数据
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);

	/**
	 *
	 * @param RoleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer RoleId);

	/**
	 * 添加角色时,保存角色和菜单的关系
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(@Param("roleId") int roleId,@Param("menuIds")Integer[] menuIds);
}
