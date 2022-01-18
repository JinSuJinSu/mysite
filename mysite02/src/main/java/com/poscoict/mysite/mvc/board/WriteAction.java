package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title"); // 게시판 제목
		String content = request.getParameter("content"); // 게시판 내용
		
		// 인증 처리(Session) 처리
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");

		BoardVo vo = new BoardVo(); // 게시판 vo 객체 생성
		BoardDao dao = new BoardDao(); // 게시판 dao 객체 생성
		boolean result = false; // insert 수행을 위해 필요한 변수
	
		if(title!=null && content!=null && !title.equals("") && !content.equals("")) {
			vo.setTitle(title);
			vo.setContent(content);
			vo.setUserNo(authUser.getNo());
			vo.setUserName(authUser.getName());
			result = dao.write(vo);
		}
		else {
			MvcUtil.redirect(request.getContextPath() + "/board",request,response);
		}
			
		if(result){
			// Board Servlet으로 돌아간후 MainAction으로 글을 조회한다.
			MvcUtil.redirect(request.getContextPath() + "/board",request,response);
		}
		
		

	}

}
