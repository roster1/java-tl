package com.db.sys.service;

import java.util.List;
import java.util.Map;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

public interface SysMenuService {
	/**
	 * 查询菜单信息
	 * @return
	 */
	List<Map<String,Object>> findObjects();
	
	/**
	 * 根据菜单id删除记录
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);

	/**
	 * 保存新添加的菜单信息
	 * @param entity
	 * @return
	 */
	int saveObject(SysMenu entity);

	/**
	 * 查询Node目录
	 * @return
	 */
	List<Node> findZtreeMenuNodes();

	/**
	 * 更改菜单的信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysMenu entity);
	
}
