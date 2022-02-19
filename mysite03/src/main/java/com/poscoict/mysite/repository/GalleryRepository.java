package com.poscoict.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 모든 갤러리를 조회한다
	public List<GalleryVo> findAll(){
		List<GalleryVo> list = sqlSession.selectList("gallery.findAll");
		return list;
	}
	
	// 갤러리 사진을 삽입한다.
	public boolean insert(GalleryVo vo) {
		int cnt=sqlSession.insert("gallery.insert", vo);
		return cnt==1;
	}
	
	// 갤러리 사진을 삭제한다.
	public boolean delete(Long no) {
		int cnt=sqlSession.insert("gallery.deleteImage", no);
		return cnt==1;
	}
	
	
	

}
