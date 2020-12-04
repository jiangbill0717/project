package com.fudan.project.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

	/* 修改握手,就是在握手协议建立之前修改其中携带的内容 */
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		if (httpSession != null) {
			// 读取session域中存储的数据
			sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
		}
		super.modifyHandshake(sec, request, response);
	}

	/**
	 * 用于扫描和注册所有携带ServerEndPoint注解的实例。
	 * <p>
	 * PS:若部署到外部容器 则无需提供此类。
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {

		return new ServerEndpointExporter();
	}
}
