package com.db.sys.controller;

import com.db.sys.entity.SysDept;
import com.db.sys.service.SysDeptService;
import com.db.sys.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dept/")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("doDeptListUI")
    public String doDeptListUI(){
        return  "sys/dept_list";
    }

    /**
     * 查询部门数据
     * @return
     */
    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return  new JsonResult(sysDeptService.findObjects());
    }

    /**
     * 删除部门信息
     * @param id
     * @return
     */
    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
        sysDeptService.deleteObject(id);
        return  new JsonResult("delete ok");
    }
    /**
     * 添加部门页面
     */
    @RequestMapping("doDeptEditUI")
    public String doDeptEditUI(){
        return  "sys/dept_edit";
    }

    /**
     * 查找Node
     * @return
     */
    @RequestMapping("doFindZTreeNodes")
    @ResponseBody
    public JsonResult doFindZTreeNodes(){
        return  new JsonResult(sysDeptService.findZtreeDeptNodes());
    }

    /**
     * 添加新的部门信息
     * @param entity
     * @return
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysDept entity){
        sysDeptService.saveObject(entity);
        return new JsonResult("save ok");
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysDept entity){
        sysDeptService.updateObject(entity);
        return new JsonResult("update ok");
    }
}
