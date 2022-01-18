package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		BoardDao dao = new BoardDao(); // 게시판 dao 객체 생성
		boolean result = false; // delete 수행을 위해 필요한 변수
		int boardNo = 0;
		
		if(no!=null) {
			boardNo = Integer.valueOf(no);
		}
		
		// 삭제같은 경우 게시글 번호로 되돌아가기가 불가능하다.
		List<BoardVo> dataList = dao.findAll();
		int startNum=0;
		for(BoardVo value : dataList) {
			if(value.getNo()==boardNo) {
				break;
			}
			else {
				startNum+=1;
			}
		}
		
		// 그래서 no를 바로 전위치로 가져다 주어야한다.
		long number;
		if(startNum==0) { // 맨앞에 있는 값을 삭제할 경우 -1로 인덱스 범위 오류가 난다.
			number = dataList.get(1).getNo();
		}
		else {
			 number = dataList.get(startNum-1).getNo();
		}
		
		result = dao.delete(boardNo);
		if(result){
			// 성공적으로 삭제를 끝냈을 경우
			// Board Servlet으로 돌아간후 MainAction으로 글을 조회한다.
			MvcUtil.redirect(request.getContextPath() + "/board?no="+number,request,response);
		}
		

	}

}
