package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
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
		UserVo result=null;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql = "select no, name, gender from user where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1,email);
			pstmt.setString(2,password);
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
				result.setPassword(password);	
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
	
	public boolean update(UserVo userVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			if(userVo.getPassword() == null || "".equals(userVo.getPassword())) {
				String sql = "update user set name=?, gender=? where no=?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setLong(3, userVo.getNo());
			} else {
				String sql = "update user set name=?, gender=?, password=? where no=?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setString(3, userVo.getPassword());
				pstmt.setLong(4, userVo.getNo());
			}

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;		
	}
	
	public UserVo findByNo(Long userNo) {
		UserVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no, name, email, gender from user where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, userNo);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);

				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;
	}
	
	

}
