package com.poscoict.mysite.vo;

public class SiteVo {
	
	private long no;
	private String title;
	private String welcome;
	private String profile;
	private String descrpition;
	
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDescrpition() {
		return descrpition;
	}
	public void setDescrpition(String descrpition) {
		this.descrpition = descrpition;
	}
	
	@Override
	public String toString() {
		return "SiteVo [no=" + no + ", title=" + title + ", welcome=" + welcome + ", profile=" + profile
				+ ", descrpition=" + descrpition + "]";
	}
	
	

}
