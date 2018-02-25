package com.Tblog.utils.test;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import com.mysql.jdbc.Connection;

public class JDBCTest {
	public static String driver = "com.mysql.jdbc.Driver";
	public static String connectionString = "jdbc:mysql://localhost:3307/tmyblog";
	public static String username = "root";
	public static String password = "mysql";
	
	String sql = "SELECT * FROM user";

	public static Connection getConnection() throws Exception {
		Class.forName(driver).newInstance();
		return (Connection) DriverManager.getConnection(connectionString, username, password);
	}

	private static void clear(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (SQLException ignored) {

		}
	}

	public static int queryCount(String sql) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = (Connection) JDBCTest.getConnection();
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				return resultSet.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			clear(null, statement, connection);
		}
	}

	@Test
	public void testJdbc() {
		int a = JDBCTest.queryCount(sql);
		System.out.println(a);
		
	}
}
