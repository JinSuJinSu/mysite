package com.poscoict.mysite.vo;

public class GuestbookVo {
	
	private long no;
	private String name;
	private String password;
	private String regDate;
	private String message;
	
	public GuestbookVo() {
		
	}
	
	public GuestbookVo(long no, String name, String password, String regDate, String message) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
		this.message = message;
	}

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	

}
