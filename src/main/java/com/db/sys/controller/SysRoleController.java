package com.db.sys.controller;

import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.JsonResult;
import com.db.sys.vo.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/role/")
@Controller
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("doRoleListUI")
    public String doRoleListUI(){
        return "sys/role_list";
    }

    /**
     * 加载页面的分页数据
     * @param name
     * @param pageCurrent
     * @return
     */
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(
            String name,Integer pageCurrent){
        PageObject<SysRole> pageObject=
                sysRoleService.findPageObjects(name,pageCurrent);
        return new JsonResult(pageObject);
    }
    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
        int row = sysRoleService.deleteObject(id);
        return  new JsonResult("delete ok");
    }

    /**
     * 添加角色页面
     * @return
     */
    @RequestMapping("doRoleEditUI")
    public String doRoleEditUI(){
        return "sys/role_edit";
    }

    /**
     * 添加角色
     * @param sysRole
     * @param menuIds
     * @return
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysRole sysRole,Integer[] menuIds){
        try {
            sysRoleService.saveObject(sysRole,menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(e);
        }
        return  new JsonResult("save ok");
    }

    /**
     * 修改角色,将该角色查询并返回
     * @param id
     * @return
     */
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        return  new JsonResult(sysRoleService.findObjectById(id));
    }
}
