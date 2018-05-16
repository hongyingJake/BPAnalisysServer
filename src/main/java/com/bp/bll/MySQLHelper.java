package com.bp.bll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zyk
 * @version ����ʱ�䣺2017��8��11�� ����2:41:06 ��˵��
 */
public class MySQLHelper {
	// �����ַ���
	private String conn_str = "jdbc:mysql://localhost:3306/MTBPDataBase?"
			+ "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
	/**
	 * ��������
	 */
	public static final String Dirname = "com.mysql.jdbc.Driver"; // ��������

	private Connection connection = null; // ���ݿ�����
	private PreparedStatement preparedStatement = null; // ����ѯ�����������

	/**
	 * ��ȡ���Ӷ���
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
	 * ���캯��
	 * 
	 * @param connStr
	 *            �����ַ��������û�������
	 */
	public MySQLHelper(String connStr) throws Exception {
		try {
			Class.forName(Dirname);// ָ����������
			// connection = DriverManager.getConnection(url, user, password);//
			// ��ȡ����
			this.connection = DriverManager.getConnection(connStr);
			this.conn_str = connStr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * �ر����Ӷ���,��ִ��query��������Ҫ�ֶ��ر�
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
	 * ִ�в�ѯ
	 * @param sql
	 * @return
	 */
	public ResultSet query(String sql, Object[] parameters) throws Exception {
		ResultSet resultSet = null;
		try {
			preparedStatement = GetConnection().prepareStatement(sql); // ׼��ִ�����
			if(parameters!=null)
			{
				for (int i = 0; i < parameters.length; i++)  
				{
					preparedStatement.setObject(i + 1, parameters[i]);
				}
			}
			resultSet = preparedStatement.executeQuery();
			//ע���ȡֵ֮����Ҫ��resultSet�ر�
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return resultSet;
	}

	/**
	 * ִ��NonQuery����
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
	 * ��ȡ����
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
