package com.db.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysLog;
/**
 * 定义用户行为日志接口
 * @author Administrator
 */
public interface SysLogDao {
	/**
	 * 基于id删除日志信息
	 * @param id
	 * @return
	 */
	int deleteObjects(
    @Param("ids")Integer... ids);
	
	/**
	 * 基于哪个用户查询那一页的记录
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	/**
	 * 基于哪个用户查询日志的记录条数
	 * @param username
	 * @return
	 */
	int getRowCount(String username);
	/**
	 * 添加日志数据
	 * @param entity
	 * @return
	 */
	int insertObject(SysLog entity);
}




