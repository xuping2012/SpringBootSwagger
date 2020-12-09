package com.xp.test.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xp.test.common.config.BasicConfig;

public class HandleMySQLUtils extends BasicConfig {

	public static Connection conn;

	/**
	 * 创建数据库连接
	 * 
	 * @param url
	 *            切记要携带database，否则在sql中必须携带database.table
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static Connection connectMySQL(String url, String user, String passwd) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, passwd);
			// 如果数据库连接可用，打印数据库连接成功的信息
			if (!conn.isClosed()) {
				System.out.println("连接数据库成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}

	/**
	 * default 创建数据库连接 修改数据
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection connectMySQL() {
		String url = "jdbc:mysql://192.107.254.161:3306/mysql?useSSL=false";// prop.getProperty("MYSQLHOST");
		String userName = "root";// prop.getProperty("MYSQLUSER");
		String password = "";// prop.getProperty("MYSQLPASSWD");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, password);
			// 如果数据库连接可用，打印数据库连接成功的信息
			if (!conn.isClosed()) {
				System.out.println("连接数据库成功");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

	/**
	 * default 查询sql
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Object[][] resultBySQL() {
		// 创建数据库连接
		Connection conn = connectMySQL();

		// list集合添加每一条sql数据
		List<Object[]> records = new ArrayList<Object[]>();
		// 执行的sql
		String sql = "select host,user from user";
		// 创建statement对象
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ResultSetMetaData res = rs.getMetaData();

			// 获取数据行的列数
			int cols = res.getColumnCount();

			// 使用next方法遍历数据结果集中的所有数据行
			while (rs.next()) {
				String files[] = new String[cols];
				for (int i = 0; i < cols; i++) {
					files[i] = rs.getString(i + 1);
				}
				records.add(files);
			}
			// 关闭数据库连接
			rs.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 二维数组
		Object[][] results = new Object[records.size()][];
		// 遍历list列表数据
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}

		return results;
	}

	/**
	 * 查询sql
	 * 
	 * @param sql
	 * @return
	 */
	public static Object[][] resultBySQL(String sql) {
		// 创建数据库连接
		Connection conn = connectMySQL();

		// list集合添加每一条sql数据
		List<Object[]> records = new ArrayList<Object[]>();
		// 创建statement对象
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ResultSetMetaData res = rs.getMetaData();

			// 获取数据行的列数
			int cols = res.getColumnCount();

			// 使用next方法遍历数据结果集中的所有数据行
			while (rs.next()) {
				String files[] = new String[cols];
				for (int i = 0; i < cols; i++) {
					files[i] = rs.getString(i + 1);
				}
				records.add(files);
			}
			// 关闭数据库连接
			rs.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 二维数组
		Object[][] results = new Object[records.size()][];
		// 遍历list列表数据
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}

		return results;
	}
}