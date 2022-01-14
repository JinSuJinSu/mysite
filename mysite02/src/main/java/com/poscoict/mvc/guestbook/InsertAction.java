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

public class InsertAction  implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String content = request.getParameter("content");
		
		if(name!=null && pass!=null && content!=null && !name.equals("") && !content.equals("") && !pass.equals("")){
			GuestbookVo vo = new GuestbookVo();
			GuestbookDao dao = new GuestbookDao();
			vo.setName(name);
			vo.setPassword(pass);
			vo.setMessage(content);
			boolean result = dao.insert(vo);
			
			if(result){
				MvcUtil.redirect(request.getContextPath() + "/guestbook",request,response);
			}
		}
			else {
				out.println("<h1>똑바로 입력하세요 뭐하십니까");
			}
		
	}

}
