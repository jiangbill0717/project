package com.fudan.project.model;

import java.util.Date;

public class Moment {

	String username;
	String content;
	Date time;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Moment(String username, String content, Date time) {
		super();
		this.username = username;
		this.content = content;
		this.time = time;
	}
	public Moment() {
		super();
	}
	
}
