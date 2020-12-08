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

	static String host;
	static String passwd;
	static String user;

	/**
	 * 传入一个表名，数据的连接
	 * 
	 * @param tablename
	 * @return
	 */
	public Object[][] getTestData(String tablename) {
		// 声明mysql数据库驱动
		String mysqlDriver = "com.mysql.cj.jdbc.Driver";
		// 配置数据库的IP地址，端口，数据库名称，账号，密码
		host = prop.getProperty("MYSQL_HOST");
		user = prop.getProperty("MYSQL_USER");
		passwd = prop.getProperty("MYSQL_PASSWD");

		// 通过设置配置信息调用getconnection另一个方法
		// Properties properties = new Properties();
		// properties.setProperty("user", user);
		// properties.setProperty("password", passwd);
		// properties.setProperty("useSSL", "false");
		// Connection connection = DriverManager.getConnection(host,
		// properties);

		List<Object[]> records = new ArrayList<Object[]>();

		try {
			// 设定驱动
			Class.forName(mysqlDriver);
			// 声明连接数据库的链接对象，使用数据库服务器的地址，用户名和密码作为参数
			Connection connection = DriverManager.getConnection(host, user,
					passwd);
			// 如果数据库连接可用，打印数据库连接成功的信息
			if (!connection.isClosed()) {
				System.out.println("连接数据库成功");
			}
			// 创建statement对象
			Statement statement = connection.createStatement();
			// 使用函数参数拼接要执行的sql语句，此语句用来获取数据表的所有数据行
			String sql = "SELECT host,user from " + tablename;

			// 声明ResultSet对象，存取执行sql语句后返回的数据结果集
			ResultSet rs = statement.executeQuery(sql);
			// 声明一个ResultSetMetaData对象
			ResultSetMetaData rsMetaDate = rs.getMetaData();
			// 获取数据行的列数
			int cols = rsMetaDate.getColumnCount();

			// 使用next方法遍历数据结果集中的所有数据行
			while (rs.next()) {
				String files[] = new String[cols];
				for (int i = 0; i < cols; i++) {
					files[i] = rs.getString(i + 1);
				}
				records.add(files);
				System.out.println(rs.getString(1) + " " + rs.getString(2));
			}
			rs.close();
			connection.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[][] results = new Object[records.size()][];
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		return results;
	}

}