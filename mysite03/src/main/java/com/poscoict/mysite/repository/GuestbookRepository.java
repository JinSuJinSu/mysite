package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 방문객 추가하기
	public boolean insert(GuestbookVo vo) {
		int cnt=sqlSession.insert("guestbook.insert", vo);
		return cnt==1;
	}
	// 방문객 전체 조회
	public List<GuestbookVo> findAll(){
		List<GuestbookVo> list = sqlSession.selectList("guestbook.findAll");
		return list;
	}
	
	// 방문객 일부 조회
	public List<GuestbookVo> findLimit(long startNo){
		List<GuestbookVo> list = sqlSession.selectList("guestbook.findLimit",startNo);
		return list;
	}
		
	// 특정 번호를 가지고 방문객 찾기
	public GuestbookVo selectOne(long no) {
		GuestbookVo vo = sqlSession.selectOne("guestbook.selectOne", no);
		return vo;
	}
	
	// 최근 방문객 찾기
	public long findRecentOne() {
		long no = sqlSession.selectOne("guestbook.findRecentOne");
		return no;
	}
	
	// 방문객 삭제하기
	public boolean delete(long no) {
		int cnt = sqlSession.delete("guestbook.delete", no);
		return cnt==1;
	}
			
}
