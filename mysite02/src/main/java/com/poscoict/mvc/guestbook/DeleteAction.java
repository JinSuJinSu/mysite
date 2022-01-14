package com.poscoict.mvc.guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		GuestbookVo vo = new GuestbookVo();
		GuestbookDao dao = new GuestbookDao();
		boolean result=false;
		
		int number = Integer.valueOf(no);
		vo = dao.selectOne(number);
		
		if(vo.getPassword().equals(password)){
			// 비밀번호가 같아야만 삭제에 성공한다.
			result=dao.delete(number);
			if(result){
				MvcUtil.redirect(request.getContextPath() + "/guestbook",request,response);
			}	
		}
		else{
			out.println("<h1>비밀번호 똑바로 입력하세요 뭐하십니까</h1>");
		}
	}

}
