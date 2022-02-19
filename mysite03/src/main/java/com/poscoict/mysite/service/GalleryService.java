package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GalleryRepository;
import com.poscoict.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryRepository galleryRepository;
	
	// 모든 갤러리 가져오기
	public List<GalleryVo> getimages(){
		List<GalleryVo> list = galleryRepository.findAll();
		return list;
	}
	
	// 갤러리 추가하기
	public boolean saveimage(GalleryVo vo) {
		return galleryRepository.insert(vo);
	}
	
	// 갤러리 삭제하기
	public boolean deleteimage(Long no) {
		return galleryRepository.delete(no);		
	}
}
