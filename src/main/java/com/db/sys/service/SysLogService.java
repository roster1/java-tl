package com.db.sys.service;

import com.db.sys.entity.SysLog;
import com.db.sys.vo.PageObject;

public interface SysLogService {
	/**
	 * 基于哪个用户查询当前页面的日志记录
	 * @param username
	 * @param currentPage
	 * @return
	 */
	PageObject<SysLog> findPageObjects(String username,Integer currentPage);
	
	int deleteObjects(Integer ... ids);
 }
