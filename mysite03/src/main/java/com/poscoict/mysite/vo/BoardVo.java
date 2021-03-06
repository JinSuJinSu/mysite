package com.poscoict.mysite.vo;

import java.util.Date;

public class BoardVo {
	private Long no;
	private String title;
	private String content;
	private Integer hit;
	private Integer groupNo;
	private Integer orderNo;
	private Integer depth;
	private String regDate;
	
	private Long userNo;
	private String name;
	
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
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public Integer getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Integer gropuNo) {
		this.groupNo = gropuNo;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", groupNo="
				+ groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", regDate=" + regDate + ", userNo=" + userNo
				+ ", name=" + name + "]";
	}
	
	
	
	
	

	
	

}
