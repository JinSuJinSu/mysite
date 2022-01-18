package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		if(no!=null) {
			int boardNo = Integer.valueOf(no);
			vo = dao.findOne(boardNo);
		}
		request.setAttribute("readvo", vo);
		MvcUtil.forward("board/view",request,response);
		}

	}


