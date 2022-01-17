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
		String originPassword = request.getParameter("originPassword");
		boolean result=false;
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		authUser.setName(name);
		authUser.setGender(gender);
		
		if(password==null || password.equals("")) { //비밀번호를 입력하지 않을 경우 회원 정보만 수정
			authUser.setPassword(originPassword);
			result = new UserDao().updateOnlyInformation(authUser);
		}
		else {
			authUser.setPassword(password); // 비밀번호를 입력했을 경우 비밀번호와 회원 정보 모두 수정
			result = new UserDao().updateWithPassword(authUser);
		}
		
		session.setAttribute("authUser",authUser);
		if(result) {
			MvcUtil.redirect(request.getContextPath(), request, response);
		}

	}

}
