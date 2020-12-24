package com.fudan.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WebSocket 消息类
 */
@JsonIgnoreProperties
public class Message {

	private MsgType type;// 消息类型

	private String username; // 发送人

	private String to;// 收信人

	private String msg; // 发送消息

	private int onlineCount; // 在线用户数

	public Message() {
		super();
	}

	public String toJsonStr() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	

	public Message(MsgType type, String username, String to, String msg, int onlineCount) {
		super();
		this.type = type;
		this.username = username;
		this.to = to;
		this.msg = msg;
		this.onlineCount = onlineCount;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}
}
