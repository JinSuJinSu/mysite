package com.poscoict.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@RestController("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	
	@GetMapping("")
	public JsonResult viewList() {
		List<GuestbookVo> list = guestbookService.getMessageList();
		return JsonResult.success(list);
	}
	
	// 방명록에 사람 추가하기
	@PostMapping("/add")
	public ResponseEntity<JsonResult> add(@RequestBody GuestbookVo guestbookVo) {
		boolean result = guestbookService.addMessage(guestbookVo);
		List<GuestbookVo> list = guestbookService.getMessageList();
		guestbookVo.setNo(list.size());
		guestbookVo.setRegDate(list.get(list.size()-1).getRegDate());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonResult.success(guestbookVo));
		
	}
	
	@DeleteMapping("/{no}")
	public JsonResult delete(@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		
		boolean result = guestbookService.deleteMessage(no, password);
		Long data = 0L;
		
		if(result) { // 방문객 삭제 성공
			data = no;
		}
		else { // 방문객 삭제 실패
			data=-1L;
		}
		return JsonResult.success(data);
	}

}
