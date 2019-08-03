package com.db.sys.controller;

import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("doUserListUI")
    public String doUserListUI(){
        return  "sys/user_list";
    }

    /**
     * 实现用户页面数据的展现
     * @param username
     * @param pageCurrent
     * @return
     */
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        return  new JsonResult(sysUserService.findPageObjects(username,pageCurrent));
    }

    /**
     * 实现用户的禁用启用
     * ...这里的修改的用户暂时设置admin,将来是登录的用户
     * @param id
     * @param valid
     * @param modifiedUser
     * @return
     */
    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid,String modifiedUser){
        sysUserService.validById(id,valid,"admin");
        return new JsonResult("update ok");
    }

    /**
     * 用户添加页面的展现
     * @return
     */
    @RequestMapping("doUserEditUI")
    public String doUserEditUI(){
        return "sys/user_edit";
    }

    /**
     * 返回用户的修改页面
     * @return
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysUser sysUser,Integer[] roleIds){
        sysUserService.saveObject(sysUser,roleIds);
        return new JsonResult("save ok");
    }

}
