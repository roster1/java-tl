package com.db.sys.dao;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuDao {
	List<Map<String,Object>> findObjects();
	/**
	 * 获取菜单下的子菜单记录数，用于判断是否有子菜单在删除
	 * @param id
	 * @return
	 */
	int getChildCount(Integer id);
	/**
	 * 根据id删除子菜单的记录
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	/**
	 * 保存新添加的菜单信息
	 */
	int insertObject(SysMenu entity);

	/**
	 * 查找Node
	 * @return
	 */
	List<Node> findZtreeMenuNodes();

	/**
	 * 更改菜单信息
	 * @param sysMenu
	 * @return
	 */
	int updateObject(SysMenu sysMenu);
}
