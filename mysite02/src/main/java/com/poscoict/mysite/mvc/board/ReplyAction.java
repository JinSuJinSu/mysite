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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String no = request.getParameter("no");
		String groupNo = request.getParameter("group_no");
		String orderNo = request.getParameter("order_no");
		String depth = request.getParameter("depth");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if(title==null || content==null || title.equals("") || content.equals("")) {
			MvcUtil.redirect(request.getContextPath() + "/board",request,response);
		}
		else {
			BoardVo vo = new BoardVo();
			BoardDao dao = new BoardDao();
			boolean updateResult=false;
			boolean insertResult=false;
			
			// 인증 처리(Session) 처리
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			vo.setGroupNo(Integer.valueOf(groupNo));
			vo.setOrderNo(Integer.valueOf(orderNo));
			vo.setDepth(Integer.valueOf(depth));
			vo.setTitle(title);
			vo.setContent(content);
			vo.setUserNo(authUser.getNo());
			updateResult = dao.replyUpdate(vo.getOrderNo(), vo.getGroupNo());
			insertResult = dao.replyWrite(vo);			
			
			if(updateResult && insertResult) {
				MvcUtil.redirect(request.getContextPath() + "/board?no=" + no,request,response);
			}
		}
		

	}

}
