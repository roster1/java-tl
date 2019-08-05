package com.db.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	  @RequestMapping("doIndexUI")
	  public String doIndexUI() {
		  return "starter";
	  } 
	  
	  @RequestMapping("doPageUI")
	  public String doPageUI() {
		  return "common/page";
	  }

	  /**登录页面的呈现*/
	  @RequestMapping("doLoginUI")
	  public String doLoginUi(){
	  	return  "login";
	  }
}
