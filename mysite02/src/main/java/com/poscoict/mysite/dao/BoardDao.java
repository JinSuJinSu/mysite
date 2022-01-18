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
	
	// 전체 게시판 글 선택(페이징 처리를 위해 필요)
	public List<BoardVo> selectAll() {
		Connection conn = JDBC.getConnection();
		List<BoardVo> list = new ArrayList<BoardVo>();
		try(Statement stmt = conn.createStatement();){
			ResultSet rs = stmt.executeQuery("select board_no, user_id, title, "
					+ "read_count, reply_count, date_format(write_date, '%Y-%m-%d %H:%i:%s') write_date from board "
					+ "order by board_no desc");
			while(rs.next()) {
				BoardVo temp = new BoardVo(rs.getInt("board_no"),rs.getString("user_id"),
						rs.getString("title"), rs.getInt("read_count"), rs.getInt("reply_count"),
						rs.getString("write_date"));
				
				list.add(temp);
			}
			}
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			JDBC.close(conn);		
		return list;
	}
	
	// 특정 번호를 가지고 있는 글을 찾기
	public BoardVo selectOne(int number) {
		Connection conn = JDBC.getConnection();
		BoardVo vo=null;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select board_no, user_id, title, content, "
					+ "read_count, reply_count, fileurl from board "
					+ "where board_no=" + number);
			if (rs.next()) {
				vo = new BoardVo();
				vo.setBoardNO(rs.getInt("board_no"));
				vo.setUserID(rs.getString("user_id"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setReadCount(rs.getInt("read_count"));	
				vo.setReplyCount(rs.getInt("reply_count"));
				vo.setFileurl(rs.getString("fileurl"));

			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return vo;
	}
	
	// 특정 글자를 포함하고 있는 글을 찾아준다.
	public List<BoardVo> search(String value, String condition){
		Connection conn = JDBC.getConnection();
		List<BoardVo> list = new ArrayList<BoardVo>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select board_no, user_id, title, content, "
					+ "read_count, reply_count, date_format(write_date, '%Y-%m-%d %H:%i:%s') write_date from board "
					+ "where " +  condition + " like " + "'%" + value + "%'" + " order by board_no desc");
			while(rs.next()) {
				BoardVo temp = new BoardVo(rs.getInt("board_no"),rs.getString("user_id"),
						rs.getString("title"), rs.getInt("read_count"), rs.getInt("reply_count"),
						rs.getString("write_date"));
				
				list.add(temp);
			}
			}
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			JDBC.close(conn);		
		return list;
				
	}
	
	// 특정 유저의 게시판 내용들만 보여준다.
	public List<BoardVo> searchUser(String user){
		Connection conn = JDBC.getConnection();
		List<BoardVo> list = new ArrayList<BoardVo>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select board_no, user_id, title, content, "
					+ "read_count, reply_count, date_format(write_date, '%Y-%m-%d %H:%i:%s') write_date from board "
					+ "where user_id='" + user + "'" + " order by board_no desc");
			while(rs.next()) {
				BoardVo temp = new BoardVo(rs.getInt("board_no"),rs.getString("user_id"),
						rs.getString("title"), rs.getInt("read_count"), rs.getInt("reply_count"),
						rs.getString("write_date"));
				list.add(temp);
			}
			}
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			JDBC.close(conn);		
		return list;
				
	}
	
	// 유저가 작성한 글을 추가해준다.
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn
				.prepareStatement("insert into board (name, title, content, write_date) "
						+ "values(?, ?, ?, now())")) {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getTitle());
				pstmt.setString(3, vo.getContent());
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
		try (PreparedStatement pstmt = conn.prepareStatement("delete from board where board_no = ?")) {
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}
	
	// 글을 수정할 때 쓰는 메소드
	public boolean update(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement("update board "
				+ "set title = ?, content = ?, fileurl = ? where board_no = ?")) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getFileurl());
			pstmt.setInt(4, vo.getBoardNO());
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
				+ "set read_count = ? where board_no = ?")) {
			pstmt.setInt(1, vo.getReadCount());
			pstmt.setInt(2, vo.getBoardNO());
			pstmt.executeUpdate();		
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}
	
	// 답변수 증가시키기는데 필요한 메소드
	public boolean replyUpdate(BoardVo vo) {
		boolean result = false;
		Connection conn = JDBC.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement("update board "
				+ "set reply_count = ? where board_no = ?")) {
			pstmt.setInt(1, vo.getReplyCount());
			pstmt.setInt(2, vo.getBoardNO());
			pstmt.executeUpdate();		
			result = true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		JDBC.close(conn);
		return result;
	}
	
	// 페이징 처리를 위한 메소드
	public List<BoardVo> selectPage(int startPage, int endPage) {
		Connection conn = JDBC.getConnection();
		List<BoardVo> list = new ArrayList<BoardVo>();
		try(Statement stmt = conn.createStatement();){
			ResultSet rs = stmt.executeQuery("select board_no, user_id, title, "
					+ "read_count, reply_count, date_format(write_date, '%Y-%m-%d %H:%i:%s') write_date from board "
					  +"order by board_no desc limit " + startPage + "," + endPage);
			while(rs.next()) {
				BoardVo temp = new BoardVo(rs.getInt("board_no"),rs.getString("user_id"),
						rs.getString("title"), rs.getInt("read_count"), rs.getInt("reply_count"),
						rs.getString("write_date"));
				
				list.add(temp);
			}
			}
			 catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			JDBC.close(conn);		
		return list;
	}

		
	}



	


