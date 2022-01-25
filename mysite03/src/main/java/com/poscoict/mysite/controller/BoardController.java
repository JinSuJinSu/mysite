package com.poscoict.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 게시판 글 전체 보기
	@RequestMapping({"","/list"})
	public String viewList(Model model, HttpSession session,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value,
			@RequestParam(value="arrow", required=true, defaultValue="") String arrow)
	{
		
		if((long[])session.getAttribute("read")!=null) {
			session.removeAttribute("read"); // 조회가 끝나면 해당 세션을 제거해준다.
		}	
		if(kwd==null || kwd.equals("")) {
			kwd="title"; // kwd, 검색 값이 비어 있을 경우는 전체 조회가 된다.
		}
		Map<String, Object> map = boardService.getContentsList(value, kwd ,page, arrow);
		model.addAttribute("map", map);
		return "board/list";
		
	}
	
	// 특정 게시판 글 선택
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model, HttpSession session){
		boolean result=false;
		BoardVo vo = boardService.getContents(no, session);
		model.addAttribute("readvo", vo);
		return "board/view";
	}
	
	// 게시판 글쓰기 화면 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(){
		return "board/write";
	}
	
	// 게시판 글쓰기
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVo vo, HttpSession session) {
		boolean result=boardService.addContents(vo, session);
		return "redirect:/board";
	}
	
	// 게시판 글수정 화면 이동
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model, HttpSession session,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=false;
		BoardVo vo = boardService.getContents(no, session);
		model.addAttribute("updatevo", vo);
		return "board/modify";
	}
	
	// 게시판 글 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String write(BoardVo vo, 
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=boardService.updateContents(vo);
		return "redirect:/board?page=" + page + "&kwd=" + kwd + "&value=" + value;
	}
	
	//게시판 글 삭제
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=boardService.deleteContents(no);
		return "redirect:/board?page=" + page + "&kwd=" + kwd + "&value=" + value;
	}
	
	
	// 게시판 답변 화면 이동
	@RequestMapping(value = "/reply/{no}", method = RequestMethod.GET)
	public String reply(@PathVariable("no") Long no, Model model, HttpSession session,
		@RequestParam(value="page", required=true, defaultValue="1") int page,
		@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
		@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=false;
		BoardVo vo = boardService.getContents(no, session);
		model.addAttribute("replyvo", vo);
		return "board/reply";
	}
	
	// 게시판 답글 달기
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(BoardVo vo, HttpSession session,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=boardService.replyContents(vo, session);
		return "redirect:/board?page=" + page + "&kwd=" + kwd + "&value=" + value;
	}
	
		
}
	
