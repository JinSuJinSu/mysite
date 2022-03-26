package com.poscoict.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.SiteRepository;
import com.poscoict.mysite.vo.SiteVo;

@Service
public class SiteService {
	
	@Autowired
	private SiteRepository siteRepository;
	
	public SiteVo getAdmin(){
		System.out.println("사이트 진입 성공");
		SiteVo vo = siteRepository.findAdmin();
		System.out.println("사이트 작동 성공");
		return vo;
	}
	
	public boolean updateAdmin(SiteVo vo) {
		boolean result=false;
		if(vo.getTitle()!=null && !vo.getTitle().equals("") && vo.getWelcome()!=null && !vo.getWelcome().equals("") && 
				vo.getDescrpition()!=null && !vo.getDescrpition().equals("") && vo.getProfile()!=null && !vo.getProfile().equals("")) {
			result = siteRepository.update(vo);	
		}
		return result;
	}
	

	
	

}
