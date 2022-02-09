package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mysql://192.168.0.66:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			
			String user = "webdb";
			String passwd = "webdb";
			conn = DriverManager.getConnection(url, user, passwd);						
		} catch (Exception e) {
			System.out.println("MYSQL 연결 실패");
			System.out.print("사유 : " + e.getMessage());
		}
		return conn;
	}
	
	
	public static void close(Connection conn) {
		try {
			if (conn != null) 
				conn.close();
		} catch (Exception e) {
			System.out.println("MYSQL 닫기 실패");
			System.out.print("사유 : " + e.getMessage());
		}
	}

}
