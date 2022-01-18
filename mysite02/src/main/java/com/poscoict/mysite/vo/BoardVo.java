package com.poscoict.mysite.vo;

import java.util.Date;

public class BoardVo {
	private Long no;
	private String title;
	private String content;
	private int hit;
	private int gropuNo;
	private int orderNo;
	private int depth;
	private String regDate;
	
	private Long userNo;
	private String userName;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getGropuNo() {
		return gropuNo;
	}
	public void setGropuNo(int gropuNo) {
		this.gropuNo = gropuNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", gropuNo="
				+ gropuNo + ", orderNo=" + orderNo + ", depth=" + depth + ", regDate=" + regDate + ", userNo=" + userNo
				+ ", userName=" + userName + "]";
	}
	
	
	
	
	

	
	

}
