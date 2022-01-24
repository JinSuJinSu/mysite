package com.poscoict.mysite.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 페이징 처리를 위해 필요한 함수
	protected int pageCheck(long boardNumber, String reply) {
		List<BoardVo> dataList = boardService.allContents("","title");
		int startNum = 1;
		for (BoardVo value : dataList) {
			if (value.getNo() == boardNumber) {
				break;
			} else {
				startNum += 1;
			}
		}
		if(reply.equals("reply")) {
			startNum += 1;
		}
		int rangeNumber = (int) Math.ceil((double) startNum / 5);
		return rangeNumber;
	}
	
	// 게시판 글 전체 보기
	@RequestMapping({"","/list"})
	public String viewList(Model model, HttpSession session,
			@RequestParam(value="no", required=true, defaultValue="0") Long no,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="position", required=true, defaultValue="") String position,
			@RequestParam(value="back", required=true, defaultValue="") String back,
			@RequestParam(value="condition", required=true, defaultValue="") String condition,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="reply", required=true, defaultValue="") String reply)
	{
		
		if((long[])session.getAttribute("read")!=null) {
			session.removeAttribute("read"); // 조회가 끝나면 해당 세션을 제거해준다.
		}
		System.out.println(no);
		System.out.println(back);
		
		// condition과 kwd가 비어 있을 경우는 전체 조회가 된다.
		if(condition==null || condition.equals("")) {
			condition="title";
		}
		if(kwd==null || condition.equals("")) {
			kwd="";
		}
		
		List<BoardVo> list = boardService.allContents(kwd, condition);
		List<Long> noList = new ArrayList<>(); // 게시글을 개수에 맞게 번호로 출력하기 위해 필요
		for(BoardVo board: list) {
			noList.add(board.getNo());
		}

		// mysql limit 시작 포인트
		int startPoint=1;
		if(no!=0) {
			page = pageCheck(no, reply); // 게시판에 있는 내용을 확인하고 돌아갔을 때 그 시점에 맞는 페이지 번호로 돌아가야 한다.
		}
		// 페이징에 맞는 게시판 목록 조회
		if(page>1) {
			startPoint = page;
			startPoint = ((startPoint - 1) * 5) + 1;
		}

		
		int startPage = 1; // 시작 페이지 지정
		int currentPage = 1; // 현재 페이지 지정
		
		if(back.equals("back")) {//편집 후 해당 페이지 번호로 돌아올 때(글 새로 쓰기 제외)
			startPage = 1+5*(startPoint/25);
			currentPage = 1 + (startPoint/5);
		}
		else {
			if(!position.equals("")){ //화살표를 클릭하지 않았을 때
				startPage = 1+5*(startPoint/25);
				currentPage = Integer.valueOf(page);
			}
			else {
				startPage = 1+5*(startPoint/25);  // 화살표를 클릭했을 시 시작페이지는 1, 6, 11 이런식으로 되고 페이징 시작점이 5개씩이다.
				currentPage = startPage;	
			}
		}
			
		int endPage = startPage + 4; // 화살표 양옆으로 5개씩 페이지가 나오므로 endpage는 stratpage에 4를 더해준다.
		if (endPage >= Math.ceil((double) list.size() / 5)) {
			endPage = (int) Math.ceil((double) list.size() / 5); // 예들 들어 게시글의 개수가 36개일 때 최대 페이지 번호는 8이고 그보다 크면 범위를 벗어나므로 최대페이지 번호로 고정시킨다.
		}

		List<BoardVo> limitList = boardService.getContents(kwd, condition, startPoint - 1); // mysql limit 시작은 1이 아닌 0부터이다.
		model.addAttribute("list", list);
		model.addAttribute("noList", noList);
		model.addAttribute("data", limitList); // 게시판 글목록 request 객체
		model.addAttribute("paging", new int[] { startPage, currentPage, endPage }); // 페이징 reuqest 객체
		model.addAttribute("search", new String[] { condition, kwd }); // 검색 reuqest 객체	
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
	public String write(BoardVo vo, HttpSession session){
		boolean result=boardService.addContents(vo, session);
		return "redirect:/board";
	}
	
	// 게시판 글수정 화면 이동
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model, HttpSession session){
		boolean result=false;
		BoardVo vo = boardService.getContents(no, session);
		model.addAttribute("updatevo", vo);
		return "board/modify";
	}
	
	// 게시판 글 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String write(BoardVo vo){
		boolean result=boardService.updateContents(vo);
		return "redirect:/board";
	}
	
	//게시판 글 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="0") Long no,
			@RequestParam(value="back", required=true, defaultValue="") String back){
		long boardNumber=boardService.deleteContents(no);
		return "redirect:/board?no=" + boardNumber + "&back=back";
	}
	
	
	// 게시판 답변 화면 이동
	@RequestMapping(value = "/reply/{no}", method = RequestMethod.GET)
	public String reply(@PathVariable("no") Long no, Model model, HttpSession session){
		boolean result=false;
		BoardVo vo = boardService.getContents(no, session);
		model.addAttribute("replyvo", vo);
		return "board/reply";
	}
	
	// 게시판 답글 달기
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(BoardVo vo, HttpSession session){
		boolean result=boardService.replyContents(vo, session);
		return "redirect:/board";
	}
	
		
}
	
