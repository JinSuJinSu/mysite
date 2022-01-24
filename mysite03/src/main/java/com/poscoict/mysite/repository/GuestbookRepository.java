package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	
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
	
	public List<GuestbookVo> findAll(){
		Connection conn=null;
		Statement stmt = null;
		ResultSet rs = null;
		List<GuestbookVo> result = new ArrayList<>();
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql = "select no, name, date_format(reg_date, \"%Y/%m/%d %H:%i:%s\") as reg_date, message "
					+ "from guestbook order by reg_date desc";
			stmt = conn.createStatement();
			// 4. 바인딩(binding)
			
			// 5. SQL 실행
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String regDate = rs.getString(3);
				String message = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setRegDate(regDate);
				vo.setMessage(message);
				result.add(vo);
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
				if(stmt!=null) {
					stmt.close();
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
	
	public boolean insert(GuestbookVo vo) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql = "insert into guestbook values(null, ?,?,?,now());";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1,vo.getName());
			pstmt.setString(2,vo.getPassword());
			pstmt.setString(3,vo.getMessage());
			
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
	
	// 특정 번호를 가지고 방문객 찾기
		public GuestbookVo selectOne(long no) {
			Connection conn=null;
			Statement stmt = null;
			ResultSet rs = null;
			GuestbookVo vo=null;
			try {
				conn = getConnection();
				stmt = conn.createStatement();
					rs = stmt.executeQuery("select no, password from guestbook where no=" + no);
					if (rs.next()) {
						vo = new GuestbookVo();
						vo.setNo(rs.getLong(1));
						vo.setPassword(rs.getString(2));
					}
			}			
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			finally {
				// 자원 정리
				try {
					if(rs!=null) {
						rs.close();
					}
					if(stmt!=null) {
						stmt.close();
					}
					if(conn!=null) {
						conn.close();
					}	
				}
				catch(SQLException e) {
					e.printStackTrace();
				}	
			}
			return vo;
		}
		
		public boolean delete(long no) {
			boolean result=false;
			Connection conn=null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement("delete from guestbook where no = ?");
				pstmt.setLong(1, no);
				pstmt.executeUpdate();
				result = true;
			} catch (SQLException se) {
				System.out.println(se.getMessage());
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
