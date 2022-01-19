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

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String no = request.getParameter("no");
		BoardVo vo = new BoardVo();
		BoardDao dao = new BoardDao();
		boolean result = false;
		
		// session 처리
		HttpSession session = request.getSession();
		if(session.getAttribute("read") == null) {
			session.setAttribute("read", new int[1]);
		}
		int[] readList = (int[])session.getAttribute("read");
		if(no!=null) {
			int boardNo = Integer.valueOf(no);
			vo = dao.findOne(boardNo);
			if(readList[0]!=boardNo) {
				vo.setHit(vo.getHit()+1);; // 게시물을 볼때마다 조회수를 1개 증가시킨다.
				result = dao.readUpdate(vo); // 증가시킨 조회수를 update해서 db 데이터를 수정해준다.
				readList[0]=boardNo;
				session.setAttribute("read", readList);
			}
		}	
		
		request.setAttribute("readvo", vo);
		MvcUtil.forward("board/view",request,response);
	}
}

	


