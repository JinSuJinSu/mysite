package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.BoardVo;


@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 모든 글의 게시판 번호를 조회한다.(검색 포함)
		public List<Long> findAll(String value, String kwd){
			Map<String, Object> map = new HashMap<>();
			map.put("kwd", kwd);
			map.put("value", value);
			List<Long> list=sqlSession.selectList("board.findAll", map);
			return list;			
		}
		
//		public int getTotalCount(String keyword) {
//			return sqlSession.selectOne("board.getTotalCount", keyword);
//		}
	
	// 특정 범위의 글을 페이징 처리해서보여줌(검색 포함)
	public List<BoardVo> limitFind(String value, String kwd, int currentPage){
		Map<String, Object> map = new HashMap<>();
		map.put("kwd", kwd);
		map.put("value", value);
		map.put("page", (currentPage-1)*5);
		List<BoardVo> list=sqlSession.selectList("board.limitFind", map);
		return list;			
	}
		
	// 특정 번호를 가지고 있는 글을 찾기
	public BoardVo findOne(long no) {
		BoardVo vo=sqlSession.selectOne("board.findOne", no);
		return vo;
	}
	
	// 유저가 작성한 글을 추가해준다.
	public boolean insert(BoardVo vo) {
		int cnt=sqlSession.insert("board.insert", vo);
		return cnt==1;	
	}
	
	// 글을 삭제해버린다.
	public boolean delete(Long no) {
		int cnt=sqlSession.delete("board.delete", no);
		return cnt==1;
	}
	
	// 글 삭제기능(댓글이 포함되어 있을 때)
	public boolean deleteUpdate(long no) {
		int cnt=sqlSession.update("board.deleteUpdate", no);
		return cnt==1;
	}
	// 글 삭제기능 수행을 위해 댓글이 달려 있는지 확인해야 한다.
	public Integer replyCheck(Integer groupNo) {
		Integer cnt=sqlSession.selectOne("board.replyCheck", groupNo);
		return cnt;
	}
	
	// 글 수정하기
	public boolean update(BoardVo vo) {
		int cnt=sqlSession.update("board.update", vo);
		return cnt==1;
	}
	
	// 댓글 달기전 orderNo, groupNo 업데이트
	public boolean replyUpdate(Integer orderNo, Integer groupNo) {
		// mybatis에서 파라미터를 map 객체로 받아야 한다.
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		map.put("groupNo", groupNo);
		int cnt=sqlSession.update("board.replyUpdate", map);
		return cnt==1;
	}
	// 댓글 추가하기
	public boolean replyInsert(BoardVo vo) {
		int cnt=sqlSession.insert("board.replyInsert", vo);
		return cnt==1;
	}
	
	// 조회수 증가시키기는데 필요한 메소드
	public boolean readUpdate(BoardVo vo) {
		int cnt=sqlSession.update("board.readUpdate", vo);
		return cnt==1;
	}
		
}



	


