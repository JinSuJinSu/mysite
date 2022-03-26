package com.poscoict.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;

@RestController("userApiController")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/checkemail")
	public Object checkEmail(@RequestParam(value="email", required=true, defaultValue="")String email) {
		UserVo userVo = userService.getUser(email);
		return JsonResult.success(userVo!=null);
	}
	
	@PostMapping("/join")
	public ResponseEntity<JsonResult> join(@RequestBody UserVo userVo) {
		userService.join(userVo);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonResult.success(userVo));
	}
	

}
