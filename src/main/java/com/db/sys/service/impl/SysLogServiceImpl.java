package com.db.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.db.common.exception.ServiceException;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;
import com.db.sys.vo.PageObject;

@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;
	/**
	 * 基于用户名和当前页码
	 * 查询
	 */
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer currentPage) {
		if(currentPage < 1 || currentPage == null) {
			throw new IllegalArgumentException("页数不能小于1");
		}
		/**
		 * 获取总记录数
		 */
		int rowCount = sysLogDao.getRowCount(username);
		if(rowCount == 0) {
			throw new ServiceException("此用户没有记录");
		}
		int pageSize = 2;
		int startIndex = (currentPage-1)*pageSize;
		List<SysLog> pageObjects = sysLogDao.findPageObjects(username, startIndex, pageSize);
		PageObject<SysLog> pageObject = new PageObject<SysLog>();
		pageObject.setPageCount((rowCount-1)/pageSize+1);//总页数
		pageObject.setPageCurrent(currentPage);			//当前页码
		pageObject.setPageSize(pageSize);				//页面大小
		pageObject.setRecords(pageObjects);				//日志记录
		pageObject.setRowCount(rowCount);				//总行数
		return pageObject;
	}
	/**
	 * 根据id删除日志记录
	 */
	@Override
	public int deleteObjects(Integer...ids) {
			//1.判定参数合法性
			if(ids==null||ids.length==0)
		    throw new IllegalArgumentException("请选择一个");
			//2.执行删除操作
			int rows;
			try{
			rows=sysLogDao.deleteObjects(ids);
			}catch(Throwable e){
			e.printStackTrace();
			//发出报警信息(例如给运维人员发短信)
			throw new ServiceException("系统故障，正在恢复中...");
			}
			//4.对结果进行验证
			if(rows==0)
			throw new ServiceException("记录可能已经不存在");
			//5.返回结果
			return rows;
	}

}









