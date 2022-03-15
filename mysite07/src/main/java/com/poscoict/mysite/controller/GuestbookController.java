package com.poscoict.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;



@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	// 방명록 페이지로 돌아가기
	@RequestMapping({"","/list"})
	public String viewList(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		System.out.println(list.get(0).getRegDate());
		model.addAttribute("list", list);	
		return "guestbook/list";
	}
	
	// 방명록에 사람 추가하기
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		// 입력이 제대로 되어있을 경우만 삽입이 가능하도록 해준다.
		System.out.println("guestbook");
		boolean result = guestbookService.addMessage(vo);
		return "redirect:/guestbook";
		
	}
	
	// 방명록 삭제하기(get 방식으로 delete jsp로 이동해준다.)
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no) {
		return "guestbook/delete";
	}
	
	// 방명록 삭제하기(post 방식으로 비밀번호가 일치하면 삭제시킨다.)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {	
		boolean result = guestbookService.deleteMessage(no,password);
		return "redirect:/guestbook/list";
	}

}
