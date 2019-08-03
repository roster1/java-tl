package com.db.sys.service;

import com.db.common.vo.Node;
import com.db.sys.entity.SysDept;

import java.util.List;
import java.util.Map;

public interface SysDeptService {
    List<Map<String,Object>> findObjects();

    /**
     * 根据id删除部门数据
     * @param id
     * @return
     */
    int deleteObject(Integer id);

    /**
     * 查找Node用于添加新部门时选择上级部门
     * @return
     */
    List<Node> findZtreeDeptNodes();
    /**
     * 添加新的部门
     * @param entity
     * @return
     */
    int saveObject(SysDept entity);

    /**
     * 更新部门信息
     * @param entity
     * @return
     */
    int updateObject(SysDept entity);
}
