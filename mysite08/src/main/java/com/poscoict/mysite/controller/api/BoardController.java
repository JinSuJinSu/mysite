package com.poscoict.mysite.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.util.WebUtil;

@RestController("boardApiController")
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	// 게시판 글 전체 보기
	@GetMapping("")
	public JsonResult viewList(@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="title") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value,
			@RequestParam(value="arrow", required=true, defaultValue="") String arrow)
	{
		
		Map<String, Object> map = boardService.getContentsList(value, kwd ,page, arrow); // 검색 값이 비어 있을 경우는 전체 조회가 된다.
		return JsonResult.success(map);
		
	}
	
	// 특정 게시판 글 선택(조회)
	@GetMapping("/view/{no}")
	public JsonResult view(@PathVariable("no") Long no){
		BoardVo vo = boardService.getContents(no);
		return JsonResult.success(vo);
	}
	
	// 게시판 글 추가하기
	@PostMapping("/write")
	public ResponseEntity<JsonResult> write(@RequestBody BoardVo boardVo) {
		boardVo.setUserNo(1L);
		System.out.println("숫자:"+ boardVo.getUserNo());
		boolean result=boardService.addContents(boardVo);	
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonResult.success(boardVo));
	}
	
	// 게시판 글 수정
	@PostMapping("/update/{no}")
	public ResponseEntity<JsonResult> write(@RequestBody BoardVo boardVo, 
			@PathVariable("no") Long no,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boardVo.setNo(no);
		boolean result=boardService.updateContents(boardVo);
		System.out.println("boardVo"+boardVo);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonResult.success(result));
	}
	
	// 게시판 글 삭제
	@DeleteMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") Long no,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="value", required=true, defaultValue="") String value){
		boolean result=boardService.deleteContents(no);
		return JsonResult.success(true);
	}


}
