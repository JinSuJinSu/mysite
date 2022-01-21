package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	// 페이징 처리를 위해 필요한 함수
	protected String pageCheck(BoardDao dao, int boardNumber, String reply) {
		List<BoardVo> dataList = dao.findAll("","title");
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
		return String.valueOf(rangeNumber);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String no = request.getParameter("no"); // 게시글의 번호
		String page = request.getParameter("page"); // 페이징 처리 번호
		String condition = request.getParameter("condition"); // 검색을 했는지 안했는지 체크
		String kwd = request.getParameter("kwd"); // 검색 키워드 값을 가져온다.
		String reply = request.getParameter("reply"); // 댓글을 달았는지 안달았는지 확인하기 위해 필요한 메소드
		BoardVo vo = new BoardVo(); // 게시판 vo 객체 생성
		BoardDao dao = new BoardDao(); // 게시판 dao 객체 생성
		int boardNo; // int 타입의 게시글 번호

		// 페이징 처리 전에 조회수 session을 삭제해버린다.
		HttpSession session = request.getSession();
		if((int[])session.getAttribute("read")!=null) {
			session.removeAttribute("read"); // 조회가 끝나면 해당 세션을 제거해준다.
		}
			
		if (no != null) {
			boardNo = Integer.valueOf(no); // 게시글 번호 받아오기
			if(reply==null) {
				reply=""; // 함수 실행할 때 null 에러를 방지하기 위해 사용
			}
			page = pageCheck(dao, boardNo, reply); // 게시판에 있는 내용을 확인하고 돌아갔을 때 그 시점에 맞는 페이지 번호로 돌아가야 한다.
		}

		// condition과 kwd가 비어 있을 경우는 전체 조회가 된다.
		if(condition==null || condition.equals("")) {
			condition="title";
		}
		if(kwd==null || condition.equals("")) {
			kwd="";
		}
		// mysql limit 시작 포인트
		int startPoint;
		List<BoardVo> list = dao.findAll(kwd, condition);
		request.setAttribute("list", list);
		List<Long> noList = new ArrayList<>(); // 게시글을 개수에 맞게 번호로 출력하기 위해 필요
		for(BoardVo board: list) {
			noList.add(board.getNo());
		}
		request.setAttribute("noList", noList);

		// 페이징에 맞는 게시판 목록 조회
		if (page == null || page.equals("1")) {
			startPoint = 1;
		}
		else {
			startPoint = Integer.valueOf(page);
			startPoint = ((startPoint - 1) * 5) + 1;
		}
		
		int startPage = 1 + (5 * (startPoint / 25)); // 페이지 1개당 글 5개고 화살표를 클릭했을 때 startPage는 25가 증가한다. 
		int endPage = startPage + 4; // 화살표 양옆으로 5개씩 페이지가 나오므로 endpage는 stratpage에 4를 더해준다.
		if (endPage >= Math.ceil((double) list.size() / 5)) {
			endPage = (int) Math.ceil((double) list.size() / 5); // 예들 들어 게시글의 개수가 36개일 때 최대 페이지 번호는 8이고 그보다 크면 범위를 벗어나므로 최대페이지 번호로 고정시킨다.
		}
		
		List<BoardVo> limitList = dao.limitFind(kwd, condition, startPoint - 1, 5); // mysql limit 시작은 1이 아닌 0부터이다.
		request.setAttribute("data", limitList); // 게시판 글목록 request 객체
		request.setAttribute("paging", new int[] { startPage, endPage }); // 페이징 reuqest 객체
		request.setAttribute("search", new String[] { condition, kwd }); // 검색 reuqest 객체
		MvcUtil.forward("board/list", request, response);
	}

}
