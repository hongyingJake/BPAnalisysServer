package com.bp.bll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zyk
 * @version 创建时间：2017年8月11日 下午2:41:06 类说明
 */
public class MySQLHelper {
	// 连接字符串
	private String conn_str = "jdbc:mysql://localhost:3306/MTBPDataBase?"
			+ "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
	/**
	 * 程序驱动
	 */
	public static final String Dirname = "com.mysql.jdbc.Driver"; // 程序驱动

	private Connection connection = null; // 数据库连接
	private PreparedStatement preparedStatement = null; // 待查询语句描述对象

	/**
	 * 获取连接对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection GetConnection() throws Exception {
		if (this.connection == null) {
			try {
				this.connection = DriverManager.getConnection(this.conn_str);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return this.connection;
	}

	/**
	 * 构造函数
	 * 
	 * @param connStr
	 *            连接字符串包含用户名密码
	 */
	public MySQLHelper(String connStr) throws Exception {
		try {
			Class.forName(Dirname);// 指定连接类型
			// connection = DriverManager.getConnection(url, user, password);//
			// 获取连接
			this.connection = DriverManager.getConnection(connStr);
			this.conn_str = connStr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 关闭连接对象,在执行query方法后需要手动关闭
	 */
	public void close() throws Exception {
		try {
			if (this.connection != null) {
				this.connection.close();
				this.connection=null;
			}
			if (this.preparedStatement != null) {
				this.preparedStatement.close();
				this.preparedStatement=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 执行查询
	 * @param sql
	 * @return
	 */
	public ResultSet query(String sql, Object[] parameters) throws Exception {
		ResultSet resultSet = null;
		try {
			preparedStatement = GetConnection().prepareStatement(sql); // 准备执行语句
			if(parameters!=null)
			{
				for (int i = 0; i < parameters.length; i++)  
				{
					preparedStatement.setObject(i + 1, parameters[i]);
				}
			}
			resultSet = preparedStatement.executeQuery();
			//注意获取值之后需要对resultSet关闭
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return resultSet;
	}

	/**
	 * 执行NonQuery操作
	 */
	public boolean executeNonquery(String sql, Object[] parameters) throws Exception {
		boolean flag = false;
		try {
			preparedStatement = GetConnection().prepareStatement(sql);
			if(parameters!=null)
			{
				for (int i = 0; i < parameters.length; i++)  
				{
					preparedStatement.setObject(i + 1, parameters[i]);
				}
			}
			preparedStatement.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.close();
		}
		return flag;
	}

	/**
	 * 获取数量
	 */
	public int getCount(String sql, Object[] parameters) throws Exception {
		int count = 0;
		try {
			preparedStatement = GetConnection().prepareStatement(sql);
			if(parameters!=null)
			{
				for (int i = 0; i < parameters.length; i++)  
				{
					preparedStatement.setObject(i + 1, parameters[i]);
				}
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.last();
			count = resultSet.getRow();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.close();
		}
		return count;
	}
}
