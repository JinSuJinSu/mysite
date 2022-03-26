package com.poscoict.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// admin 유저를 찾는다.
	public SiteVo findAdmin() {
		System.out.println("레파지토리진입성공");
		SiteVo vo=sqlSession.selectOne("site.findAdmin");
		System.out.println("레파지토리 작동 성공");
		return vo;
	}
	
	// admin 유저 정보를 업데이트 한다.
	public boolean update(SiteVo vo) {
		int cnt=sqlSession.update("site.update", vo);
		return cnt==1;
	}

}
