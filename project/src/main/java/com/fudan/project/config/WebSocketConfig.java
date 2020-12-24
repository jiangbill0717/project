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
