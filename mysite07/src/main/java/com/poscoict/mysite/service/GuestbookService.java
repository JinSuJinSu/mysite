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

	public boolean deleteMessage(Long no, String password) {
		GuestbookVo vo = guestbookRepository.selectOne(no);
		boolean result=false;
		if(vo.getNo()==no && vo.getPassword().equals(password)) {
			guestbookRepository.delete(no);
			result=true;
		};
		return result;
	}

	// 방명록에 추가할 때 값이 비어있거나 null인 경우 추가를 하면 안된다.
	public boolean addMessage(GuestbookVo vo) {
		boolean result=false;
		if (vo.getName() != null && vo.getPassword() != null && vo.getMessage() != null && !vo.getName().equals("")
				&& !vo.getPassword().equals("") && !vo.getMessage().equals("")) {
			guestbookRepository.insert(vo);
			result=true;		
		}
		return result;

	}

}
