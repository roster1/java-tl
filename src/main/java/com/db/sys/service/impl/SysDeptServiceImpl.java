package com.db.sys.service.impl;

import com.db.common.exception.ServiceException;
import com.db.common.vo.Node;
import com.db.sys.dao.SysDeptDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.entity.SysDept;
import com.db.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 查询部门信息
     * @return
     */
    @Override
    public List<Map<String, Object>> findObjects() {
        return sysDeptDao.findObjects();
    }

    @Override
    public int deleteObject(Integer id) {
        //1.判断是否为空
        if(id == null || id<=0)
            throw new IllegalArgumentException("参数有误,id="+id);
        //2.如果不为零查询是否有子元素
        int rows = sysDeptDao.getChildCount(id);
        if(rows >=1)
            throw new ServiceException("请先删除子元素");
        //3.如果子元素为零，看是否还有员工
        int count = sysUserDao.getUserCountByDeptId(id);
        if(count>=1)
            throw new ServiceException("请先删除部门下的员工");
        //4.如果员工为零，删除部门数据
        int row = sysDeptDao.deleteObject(id);
        if(row == 0)
            throw new ServiceException("此信息可能已经不存在");
        return row;
    }

    /**
     * 查找Node
     * @return
     */
    @Override
    public List<Node> findZtreeDeptNodes() {
        return sysDeptDao.findZtreeDeptNodes();
    }

    /**
     * 添加新的部门
     * @param entity
     * @return
     */
    @Override
    public int saveObject(SysDept entity) {
        //1.判断是否为空
        if(entity == null)
            throw new ServiceException("对象不能为空");
        //2.如果不为空，判断名称是否为空
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("对象的名称不能为空");
        //3.如果名称不为空，就将新的部门信息入库
        int row;
        try {
            row = sysDeptDao.insertObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("添加失败");
        }
        return sysDeptDao.insertObject(entity);
    }

    /**
     * 更新部门信息
     * @param entity
     * @return
     */
    @Override
    public int updateObject(SysDept entity) {
        //1.合法验证
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("部门名不能为空");

        //2.更新数据
        int rows=sysDeptDao.updateObject(entity);
        if(rows==0)
            throw new ServiceException("记录可能已经不存在");
        //3.返回数据
        return rows;
    }
}
