package com.poscoict.mysite.vo;

public class GalleryVo {
	
	private Long no;
	private String url;
	private String comments;
	
	public GalleryVo() {
		
	}
	
	public GalleryVo(Long no, String url, String comments) {
		this.no = no;
		this.url = url;
		this.comments = comments;
	}
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	

}
