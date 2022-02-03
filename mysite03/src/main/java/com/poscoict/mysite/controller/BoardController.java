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

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.util.WebUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 게시판 글 전체 보기
	@RequestMapping({"","/list"})
	public String viewList(Model model, HttpSession session,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="title") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value,
			@RequestParam(value="arrow", required=true, defaultValue="") String arrow)
	{
		
		if((long[])session.getAttribute("read")!=null) {
			session.removeAttribute("read"); // 조회가 끝나면 해당 세션을 제거해준다.
		}
		
	
		Map<String, Object> map = boardService.getContentsList(value, kwd ,page, arrow); // 검색 값이 비어 있을 경우는 전체 조회가 된다.
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
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(){
		return "board/write";
	}
	
	// 게시판 글쓰기
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setUserNo(authUser.getNo());
		boolean result=boardService.addContents(vo);
		return "redirect:/board";
	}
	
	// 게시판 글수정 화면 이동
	@Auth
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=false;
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("updatevo", vo);
		return "board/modify";
	}
	
	// 게시판 글 수정
	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo, 
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=boardService.updateContents(vo);
		return "redirect:/board?page=" + page + "&kwd=" + kwd + "&value=" + WebUtil.encodeURL(value, "UTF-8");
	}
	
	//게시판 글 삭제
	@Auth
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=boardService.deleteContents(no);
		return "redirect:/board?page=" + page + "&kwd=" + kwd + "&value=" + WebUtil.encodeURL(value, "UTF-8");
	}
	
	
	// 게시판 답변 화면 이동
	@RequestMapping(value = "/reply/{no}", method = RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model,
		@RequestParam(value="page", required=true, defaultValue="1") int page,
		@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
		@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=false;
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("replyvo", vo);
		return "board/reply";
	}
	
	// 게시판 답글 달기
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@AuthUser UserVo authUser, BoardVo vo,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		vo.setUserNo(authUser.getNo());
		boolean result=boardService.replyContents(vo);
		return "redirect:/board?page=" + page + "&kwd=" + kwd + "&value=" + WebUtil.encodeURL(value, "UTF-8");
	}
	
		
}
	
