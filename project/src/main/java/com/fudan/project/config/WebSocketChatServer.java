package com.fudan.project.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudan.project.model.Message;
import com.fudan.project.model.MsgType;

/**
 * WebSocket 消息服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Component
@ServerEndpoint(value = "/chat",configurator = WebSocketConfig.class)
public class WebSocketChatServer {

	/**
	 * 全部在线会话 PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
	 */
	private static Map<String, List<Session>> onlineSessions = new ConcurrentHashMap<>();

	
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 当客户端打开连接：1.添加会话对象 2.更新在线人数
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		System.out.print("someone entered");
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
			message.setOnlineCount(onlineSessions.size());
			switch (message.getType()) {
			case ENTER:
				if (onlineSessions.get(message.getUsername()) == null) {
					onlineSessions.put(message.getUsername(), new ArrayList<Session>());
				}
				onlineSessions.get(message.getUsername()).add(session);
				break;
			case SPEAKTOALL:
				sendMessageToAll(message,session);
				break;
			case SPEAK:
				sendMessageToTarget(message);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 当关闭连接：1.移除会话对象 2.更新在线人数
	 */
	@OnClose
	public void onClose(Session session) {
		for (Entry<String, List<Session>> entry : onlineSessions.entrySet()) {
			entry.getValue().remove(session);
			if (entry.getValue().size() == 0) {
				onlineSessions.remove(entry.getKey());
			}
		}
		sendMessageToAll(new Message(MsgType.QUIT, "", "", "", onlineSessions.size()), session);
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
	 * @param session2 
	 */
	private static void sendMessageToAll(Message msg, Session session2) {
		onlineSessions.values().stream().flatMap(Collection::stream).forEach(session -> {
			try {
				session.getBasicRemote().sendText(msg.toJsonStr());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 公共方法：发送信息给所有人
	 */
	private static void sendMessageToTarget(Message msg) {
		String target = msg.getTo();
		onlineSessions.get(target).stream().forEach(session -> {
			try {
				session.getBasicRemote().sendText(msg.toJsonStr());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public Set<String> getOnlineUsers() {
		return onlineSessions.keySet();
	}
}
