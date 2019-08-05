package com.db.sys.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;
import com.db.sys.vo.JsonResult;
import com.db.sys.vo.PageObject;
@Controller
@RequestMapping("/log/")
public class SysLogController {
	@Autowired
	
	private SysLogService sysLogService;
	
	@RequestMapping("doLogListUI")
	public String doLogListUI() {
		return "sys/log_list";
	}
	
	 @GetMapping("doFindPageObjects")
	 @ResponseBody
	  public JsonResult doFindPageObjects(
			  Integer pageCurrent,
			  String username) {
		 PageObject<SysLog> pageObject = null;
		 try {
			 pageObject = sysLogService.findPageObjects(username, pageCurrent);
		 } catch (Exception e) {
			 e.printStackTrace();
			 return  new JsonResult(e.getMessage());
		 }
		 return new JsonResult(pageObject);
	  }
	 /**
	  * 删除日志记录
	  * @param ids
	  * @return
	  */
	 @PostMapping("doDeleteObjects")
	 @ResponseBody
	 public JsonResult doDeleteObjects(@Param("ids")Integer... ids) {
		 sysLogService.deleteObjects(ids);
		 return new JsonResult("delete ok");
	 }
}
