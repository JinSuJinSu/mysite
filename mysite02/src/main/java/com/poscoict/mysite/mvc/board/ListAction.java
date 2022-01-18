package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;


public class ListAction implements Action{
	
	// 페이징 처리를 위해 필요한 함수
	protected String pageCheck(BoardDao dao, int boardNumber) {
		List<BoardVo> dataList = dao.findAll();
		int startNum=1;
		for(BoardVo value : dataList) {
			if(value.getNo()==boardNumber) {
				break;
			}
			else {
				startNum+=1;
			}
		}
		int rangeNumber = (int)Math.ceil((double)startNum/10);
		return String.valueOf(rangeNumber);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageCount=10;
		int currentPage = 2;
		int nextPage = -1;
//		int startPage = 3;
		int prePage = 2;
		
//		Map<K, V> m;
//		m.put(response, m)
		String no =  request.getParameter("no"); // 게시글의 번호
		String page = request.getParameter("page"); // 페이징 처리 번호
		String condition = request.getParameter("condition"); // 검색을 했는지 안했는지 체크
		String kwd = request.getParameter("kwd"); // 검색 키워드 값을 가져온다.
		BoardVo vo = new BoardVo(); // 게시판 vo 객체 생성
		BoardDao dao = new BoardDao(); // 게시판 dao 객체 생성
		int boardNo;
		
		if(no!=null) {
		boardNo = Integer.valueOf(no); // 게시글 번호 받아오기
		page = pageCheck(dao,boardNo); // 게시판에 있는 내용을 확인하고 돌아갔을 때 그 시점에 맞는 페이지 번호로 돌아가야 한다.
		}

	    // 페이징 시작 번호
		int startPage;
		if(page==null) {
			startPage = 1;			
		}
		else if(page.equals("1")) {
			startPage=1;		
		}
		else {
			startPage = Integer.valueOf(page);
			startPage = ((startPage-1)*10)+1;
		}
		
		dao = new BoardDao();
		
		if(condition!=null && !condition.equals("") && kwd!=null && !kwd.equals("")) {
			List<BoardVo> list = dao.search(kwd,condition);
			request.setAttribute("list", list);
			
			// 페이징에 맞는 게시판 목록 조회
			List<BoardVo> limitList = dao.limitSearch(kwd, condition, startPage-1, 10);
			request.setAttribute("data", limitList);
		}
		else {
			List<BoardVo> list = dao.findAll();
			request.setAttribute("list", list);
			
			// 페이징에 맞는 게시판 목록 조회
			List<BoardVo> limitList = dao.selectPage(startPage-1, 10);
			request.setAttribute("data", limitList);	
		}

		MvcUtil.forward("board/list", request, response);
	}

}
