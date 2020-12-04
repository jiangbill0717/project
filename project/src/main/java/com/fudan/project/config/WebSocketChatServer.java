package com.fudan.project.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudan.project.model.Message;

/**
 * WebSocket 聊天服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Component
@ServerEndpoint(value = "/chat",configurator = WebSocketConfig.class)
public class WebSocketChatServer {

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<User, List<Session>> onlineSessions = new ConcurrentHashMap<>();


    private ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    	HttpSession httpsession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
    	SecurityContextImpl securityContext = (SecurityContextImpl) httpsession.getAttribute("SPRING_SECURITY_CONTEXT");
		User user = (User) securityContext.getAuthentication().getPrincipal();
    	if(onlineSessions.get(user) == null) {
    		onlineSessions.put(user, new ArrayList<Session>());
    	}
    	onlineSessions.get(user).add(session);
        sendMessageToAll(Message.jsonStr(Message.ENTER, "", "", onlineSessions.size()));
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
		try {
			Message message = objectMapper.readValue(jsonStr, Message.class);
			sendMessageToAll(Message.jsonStr(Message.SPEAK, message.getUsername(), message.getMsg(), onlineSessions.size()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session) {
    	for(Entry<User, List<Session>> entry :onlineSessions.entrySet()) {
    		entry.getValue().remove(session);
    		if (entry.getValue().size() == 0) {
				onlineSessions.remove(entry.getKey());
			}
    	}
        sendMessageToAll(Message.jsonStr(Message.QUIT, "", "", onlineSessions.size()));
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendMessageToAll(String msg) {
        onlineSessions.values().stream().flatMap(Collection::stream).forEach(session -> {
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
    }

    public Set<User> getOnlineUsers() {
    	return onlineSessions.keySet();
    }
}
