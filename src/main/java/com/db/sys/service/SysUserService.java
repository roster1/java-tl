package com.db.sys.service;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.PageObject;
import com.db.sys.vo.SysUserDeptVo;

import java.util.List;
import java.util.Map;

public interface SysUserService {

    PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);

    /*实现用户的禁用启用*/
    int validById(Integer id,Integer valid,String modifiedUser);

    /*保存添加的用户*/
    int saveObject(SysUser sysUser, Integer[] roleIds);

    /*修改用户时,1.根据id回显要修改的用户*/
    Map<String,Object> findObjectById(Integer id);
    /*2.将修改的用户保存*/
    int updateObject(SysUser entity, Integer[] roleIds);
}
