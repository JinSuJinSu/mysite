package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		BoardVo vo = new BoardVo();
		BoardDao dao = new BoardDao();
		boolean result=false;
		
		if(title==null || content==null || title.equals("") || content.equals("")) {
			MvcUtil.redirect(request.getContextPath() + "/board?no=" + no + "&back=back",request,response);
		}
		else {
			if(no!=null) {
				long boardNo = Long.valueOf(no);
				vo.setNo(boardNo);
				vo.setTitle(title);
				vo.setContent(content);
				result = dao.modify(vo);
			}
			if(result) {
				MvcUtil.redirect(request.getContextPath() + "/board?no=" + no + "&back=back",request,response);
			}
		}
		
		}

}


