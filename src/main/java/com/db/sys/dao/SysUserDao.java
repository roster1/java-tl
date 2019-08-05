package com.db.sys.dao;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDao {
    /**
     * 根据id查询有几个员工
     * @param deptId
     * @return
     */
    int getUserCountByDeptId(Integer deptId);

    /**
     * 1.查询有多少条记录
     * 2.通过起始位置和每页数据进行分页查询
     * @param username
     * @return
     */
    int getRowCount(@Param("username")String username);
    List<SysUserDeptVo> findPageObjects(@Param("username") String username,
                                        @Param("startIndex")int startIndex,
                                        @Param("pageSize")int pageSize);

    /**
     * 实现用户的禁用启用
     * @param id
     * @param valid
     * @param modefiedUser
     * @return
     */
    int validById(@Param("id")Integer id,
                  @Param("valid")Integer valid,
                  @Param("modifiedUser")String modefiedUser);

    /*新添加的用户入库*/
    int insertObject(SysUser entity);

    /*修改用户时,
    1.将用户的数据回显 2.将修改的用户数据入库*/
    SysUserDeptVo findObjectById(Integer id);
    /*2.将修改的用户数据入库*/
    int updateObject(SysUser user);

    /*用户的登录认证,查询用户的信息*/
    SysUser findUserByUserName(String username);
}
