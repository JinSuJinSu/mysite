package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.poscoict.mysite.exception.UserRepositoryException;
import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {
		// TODO Auto-generated method stub
		int count = sqlSession.insert("user.insert",vo);
		return count==1;
	}
		
	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException{
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		UserVo vo = sqlSession.selectOne("user.findByEmailAndPassword",map);
		return vo;
	}
		
	public boolean update(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		return count==1;
	}
		
	
	public UserVo findByNo(Long userNo) {
		UserVo result = sqlSession.selectOne("user.findByNo", userNo);
		return result;
	}

}
