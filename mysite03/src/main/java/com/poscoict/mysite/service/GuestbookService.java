package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GuestbookRepository;
import com.poscoict.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}

	
	
	// 방문객 삭제하기
	public boolean deleteMessage(Long no, String password) {
		GuestbookVo vo = guestbookRepository.selectOne(no);
		boolean result=false;
		if(vo.getNo()==no && vo.getPassword().equals(password)) {
			guestbookRepository.delete(no);
			result=true;
		};
		return result;
	}
	
	// 방명록에 방문객 추가하기
	public boolean addMessage(GuestbookVo vo) {
		boolean result = guestbookRepository.insert(vo);
		return result;	
	}
	
	// 최근 방문객 번호 찾기
	public long getRecentOne() {
		long no = guestbookRepository.findRecentOne();
		return no;
	}
	

}
