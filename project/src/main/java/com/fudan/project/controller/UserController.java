package com.fudan.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fudan.project.config.WebSocketChatServer;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private WebSocketChatServer chatServer;

	@GetMapping("/onlineUsers")
	public ResponseEntity<List<String>> getOnlineUsers() {
		return ResponseEntity.ok(chatServer.getOnlineUsers().stream().map(user -> {
			return user.getUsername();
		}).collect(Collectors.toList()));
	}

}
