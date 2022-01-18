package com.poscoict.mysite.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;


public class BoardDao {
	
	// 게시판 글 전체 선택
	public List<BoardVo> findAll() {
		Connection conn = JDBC.getConnection();
		List<BoardVo> list = new ArrayList<BoardVo>();
		try(Statement stmt = conn.createStatement();){
			ResultSet rs = stmt.executeQuery("select b.no from board b join user u on b.user_no=u.no " + 
								"order by b.g_no desc, b.o_no asc");
			while(rs.next()) {
				Long no = rs.getLong("no");	
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				list.add(vo);
			}
			}
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			JDBC.close(conn);		
		return list;
	}
	
	// 게시판 글 일부 선택
		public List<BoardVo> selectPage(int startPage, int endPage) {
			Connection conn = JDBC.getConnection();
			List<BoardVo> list = new ArrayList<BoardVo>();
			try(Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("select b.no, title, name, hit, depth, date_format(reg_date, '%Y-%m-%d %H:%i:%s') reg_date from board b " + 
						"join user u on b.user_no=u.no " + 
						"order by b.g_no desc, b.o_no asc limit " + startPage + "," + endPage);
				while(rs.next()) {
					Long no = rs.getLong("no");
					String title = rs.getString("title");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					int depth = rs.getInt("depth");
					String regDate =  rs.getString("reg_date");
					
					BoardVo vo = new BoardVo();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setUserName(name);
					vo.setHit(hit);
					vo.setDepth(depth);
					vo.setRegDate(regDate);
					list.add(vo);
				}
				}
				 catch (SQLException se) {
					System.out.println(se.getMessage());
				}
				JDBC.close(conn);		
			return list;
		}
	
	// 특정 번호를 가지고 있는 글을 찾기
	public BoardVo findOne(int number) {
		Connection conn = JDBC.getConnection();
		BoardVo vo=null;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select b.no, title, content, name, hit, g_no, o_no, depth from board b " + 
					"join user u on b.user_no=u.no where b.no=" + number);
			if (rs.next()) {
				vo = new BoardVo();
				vo.setNo((long) number);
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setHit(rs.getInt("hit"));
				vo.setUserName(rs.getString("name"));
				vo.setGroupNo(rs.getInt("g_no"));
				vo.setOrderNo(rs.getInt("o_no"));
				vo.setDepth(rs.getInt("depth"));
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return vo;
	}

//	
	// 특정 글자를 포함하고 있는 글을 찾아준다.
	public List<BoardVo> search(String value, String condition){
		Connection conn = JDBC.getConnection();
		List<BoardVo> list = new ArrayList<BoardVo>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select no from board "
					+ "where " +  condition + " like " + "'%" + value + "%'");
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				Long no = rs.getLong("no");
				vo.setNo(no);
				list.add(vo);
			}
			}
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			JDBC.close(conn);		
		return list;			
	}
	
	// 특정 글자를 포함하고 있는 글을 페이징 처리해서 찾아준다.
		public List<BoardVo> limitSearch(String value, String condition, int startPage, int endPage){
			Connection conn = JDBC.getConnection();
			List<BoardVo> list = new ArrayList<BoardVo>();
			try (Statement stmt = conn.createStatement()) {
				ResultSet rs = stmt.executeQuery("select b.no, title, name, hit, depth, date_format(reg_date, '%Y-%m-%d %H:%i:%s') reg_date from board b " + 
						"join user u on b.user_no=u.no "
						+ "where " +  condition + " like " + "'%" + value + "%'" + "order by b.g_no desc, b.o_no asc limit " + startPage + "," + endPage);
				while(rs.next()) {
					Long no = rs.getLong("no");
					String title = rs.getString("title");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					int depth = rs.getInt("depth");
					String regDate =  rs.getString("reg_date");
					
					BoardVo vo = new BoardVo();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setUserName(name);
					vo.setHit(hit);
					vo.setDepth(depth);
					vo.setRegDate(regDate);
					list.add(vo);
				}
				}
				 catch (SQLException se) {
					System.out.println(se.getMessage());
				}
				JDBC.close(conn);		
			return list;			
		}
	
	
	// 유저가 작성한 글을 추가해준다.
	public boolean write(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn
				.prepareStatement("insert into board values(null, ?, ?, 0, (select * from(select max(ifnull(g_no,0))+1 from board) as temp), 1, 1, now(), ?)")) {
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUserNo());
				pstmt.executeUpdate();
				result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;	
	}
	
	// 글을 삭제해버린다.
	public boolean delete(int number) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ?")) {
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}
	
	// 글 수정하기
	public boolean modify(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement("update board "
				+ "set title = ?, content = ? where no = ?")) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			pstmt.executeUpdate();		
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}
	
	// 댓글 달기전 orderNo, groupNo 업데이트
	public boolean replyUpdate(int orderNo, int groupNo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement("update board set o_no=o_no+1 where o_no>" + orderNo + " and g_no=" + groupNo)) {
			pstmt.executeUpdate();		
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}
	// 댓글 추가하기
	public boolean replyWrite(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn
				.prepareStatement("insert into board values(null, ?, ?, 0, ?, ?, ?, now(), ?)")) {
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setInt(3, vo.getGroupNo());
				pstmt.setInt(4, vo.getOrderNo()+1);
				pstmt.setInt(5, vo.getDepth()+1);
				pstmt.setLong(6,  vo.getUserNo());
				pstmt.executeUpdate();
				result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;	
	}
	
	
	// 조회수 증가시키기는데 필요한 메소드
	public boolean readUpdate(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement("update board "
				+ "set hit = ? where no = ?")) {
			pstmt.setInt(1, vo.getHit());
			pstmt.setLong(2, vo.getNo());
			pstmt.executeUpdate();		
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}


		
}



	


