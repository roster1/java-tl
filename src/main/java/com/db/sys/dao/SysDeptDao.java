package com.db.sys.dao;

import com.db.common.vo.Node;
import com.db.sys.entity.SysDept;

import java.util.List;
import java.util.Map;

public interface SysDeptDao {
    /**
     * 查询部门数据
     * @return
     */
    List<Map<String,Object>> findObjects();

    /**
     * 根据id查询是否有子元素
     * @param id
     * @return
     */
    int getChildCount(Integer id);

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    int deleteObject(Integer id);

    List<Node> findZtreeDeptNodes();
    /**
     * 添加一个新的部门
     * @param entity
     * @return
     */
    int insertObject(SysDept entity);

    /**
     * 修改部门信息
     * @param entity
     * @return
     */
    int updateObject(SysDept entity);
}
