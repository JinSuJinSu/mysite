package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		if(name==null || password==null || gender==null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=updateform", request, response);
			return;
		}
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		authUser.setName(name);
		authUser.setPassword(password);
		authUser.setGender(gender);
		boolean result = new UserDao().update(authUser);
		if(result) {
			MvcUtil.redirect(request.getContextPath(), request, response);
		}

	}

}
