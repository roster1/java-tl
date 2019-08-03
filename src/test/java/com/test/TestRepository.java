package com.test;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.db.sys.dao.SysLogDao;

public class TestRepository extends TestBase {
	 @Test
	 public void testProperties() {
		 Properties pros=//Map
		 ctx.getBean("cfg", Properties.class);
		 System.out.println(pros);
	 }
	 @Test
	 public void testDruidDataSource() throws SQLException {
		 DataSource dataSource=
		 ctx.getBean("dataSource",DataSource.class);
		 System.out.println(
				 dataSource.getConnection());
	 }
	 
	 @Test
	 public void testSqlSessionFactory() {
		 SqlSessionFactory ssf=
		 ctx.getBean("sqlSessionFactory",SqlSessionFactory.class);
	     System.out.println(ssf);
	 }
	 @Test
	 public void testDefaultSysLogDao() {
		 SysLogDao logDao=
		 ctx.getBean("defaultSysLogDao", SysLogDao.class);
	     int rows=
	     logDao.deleteObjects(10,11,12);
	     System.out.println(rows);
	 }
	 @Test
	 public void testSysLogDao() {
		 SysLogDao logDao=
				 ctx.getBean("sysLogDao", SysLogDao.class);
		 int rows=logDao.deleteObjects(13,14);
		 System.out.println(rows);
	 }
	 
}






