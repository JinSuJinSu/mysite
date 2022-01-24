package com.poscoict.mysite.service;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 글쓰기
	public boolean addContents(BoardVo vo, HttpSession session) {
		boolean result=false;
		if(vo.getTitle()!=null && !vo.getTitle().equals("") && vo.getContent()!=null && !vo.getContent().equals("")) {
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			vo.setUserNo(authUser.getNo());
			result=boardRepository.write(vo);
		}
		return result;		
	}
	
	// 답글 달기
	public boolean replyContents(BoardVo vo, HttpSession session) {
		boolean result=false;
		System.out.println(vo.getContent());
		System.out.println(vo.getTitle());
		System.out.println(vo.getDepth());
		System.out.println(vo.getGroupNo());
		System.out.println(vo.getOrderNo());
		if(vo.getTitle()!=null && !vo.getTitle().equals("") && vo.getContent()!=null && !vo.getContent().equals("")) {
			boolean updateResult=false;
			boolean insertResult=false;
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			vo.setUserNo(authUser.getNo());
			updateResult = boardRepository.replyUpdate(vo.getOrderNo(), vo.getGroupNo());
			insertResult = boardRepository.replyWrite(vo);	
			result=true;
		}
		return result;
	}
	
	// 글보기
	public BoardVo getContents(Long no, HttpSession session){
		BoardVo vo = boardRepository.findOne(no);
		boolean result=false;
		// session 처리
		if(session.getAttribute("read") == null) {
			session.setAttribute("read", new long[1]);
		}
		long[] readList = (long[])session.getAttribute("read");
			if(readList[0]!=no) {
				vo.setHit(vo.getHit()+1);; // 게시물을 볼때마다 조회수를 1개 증가시킨다.
				result = boardRepository.readUpdate(vo); // 증가시킨 조회수를 update해서 db 데이터를 수정해준다.
				readList[0]=no;
				session.setAttribute("read", readList);
			}
		return vo;
	}
	
	// 글 수정
	public boolean updateContents(BoardVo vo) {
		boolean result=false;
		System.out.println(vo);
		if(vo.getTitle()!=null && !vo.getTitle().equals("") && vo.getContent()!=null && !vo.getContent().equals("")) {
			result = boardRepository.modify(vo);	
		}
		return result;
	}
	
	// 글 삭제
	public boolean deleteContents(Long no) {
		BoardVo vo = boardRepository.findOne(no);
		int cnt = boardRepository.replyCheck(vo.getGroupNo());
		boolean result=false;
		System.out.println(vo.getNo());
		if(cnt>1) {
			// 댓글이 달려 있는 글일 경우
			result = boardRepository.deleteUpdate(vo.getNo());
		}
		else {
			result = boardRepository.delete(no);
		}
		System.out.println(result);
		return result;
	}
	
	// 전체글 가져오기
	public List<BoardVo> allContents(String value, String condition){
		List<BoardVo> list = boardRepository.findAll(value,condition);
		return list;
	}
	
	
	// 글 리스트(찾기 결과)
	public List<BoardVo> getContents(String value, String condition, int startPoint){
//		Map<String, Object> map = new HashMap<>();
//		int currentPage, String keyword(함수의 원래 인자(나중에 풀기))

		List<BoardVo> list = boardRepository.limitFind(value,condition,startPoint);
		return list;
//		
//		map.put("list", null);
//		map.put("totalCount", 0);
//		map.put("-", map);
//		return map;
			
	}
	
	// 게시판으로 돌아가기(3페이지에서 조회,수정,삭제, 취소 작업을 했을 때 페이지 선택 상태로 그대로 돌아가야 한다.)

	

}
