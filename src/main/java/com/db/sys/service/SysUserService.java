package com.db.sys.service;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.PageObject;
import com.db.sys.vo.SysUserDeptVo;

import java.util.List;

public interface SysUserService {

    PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);

    /*实现用户的禁用启用*/
    int validById(Integer id,Integer valid,String modifiedUser);

    /*保存添加的用户*/
    int saveObject(SysUser sysUser, Integer[] roleIds);
}
