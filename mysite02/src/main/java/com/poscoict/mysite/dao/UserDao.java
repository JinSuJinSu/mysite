package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.poscoict.mysite.vo.UserVo;

public class UserDao {
	
	private Connection getConnection() throws SQLException{
		Connection conn=null;
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			String user = "webdb";
			String passwd = "webdb";
			conn = DriverManager.getConnection(url, user, passwd);
		}
		catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e.getMessage());
		}
		return conn;
	}
	
	public boolean insert(UserVo vo) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql = "insert into user values(null, ?, ?, ?, ?, now());";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1,vo.getName());
			pstmt.setString(2,vo.getEmail());
			pstmt.setString(3,vo.getPassword());
			pstmt.setString(4,vo.getGender());
			
			// 5. SQL 실행
			int count = pstmt.executeUpdate(); //insert가 된 수를 말한다.
			result = count==1;
		}
		catch (SQLException e) {
			System.out.println("error : " + e);
		}
		finally {
			// 자원 정리
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}	
			}
			catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		UserVo result=null;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql = "select no, name from user where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1,email);
			pstmt.setString(2,password);
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}

		}
		catch (SQLException e) {
			System.out.println("error : " + e);
		}
		finally {
			// 자원 정리
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}	
			}
			catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	public boolean update(UserVo vo) {
		boolean result = false;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql = "update board user set name = ?, password = ?, gender = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1,vo.getName());
			pstmt.setString(2,vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
				
			// 5. SQL 실행
			int count = pstmt.executeUpdate(); //insert가 된 수를 말한다.
			result = count==1;
		}
		catch (SQLException e) {
			System.out.println("error : " + e);
		}
		finally {
			// 자원 정리
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}	
			}
			catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		return result;
		
		
	}
	
	

}
