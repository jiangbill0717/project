package com.fudan.project.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fudan.project.config.WebSocketChatServer;
import com.fudan.project.model.Moment;
import com.fudan.project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private WebSocketChatServer chatServer;

	@Autowired 
	private UserService userService;
	
	@GetMapping("/onlineUsers")
	public ResponseEntity<Set<String>> getOnlineUsers() {
		return ResponseEntity.ok(chatServer.getOnlineUsers());
	}
	
	@PostMapping("/moment")
	public ResponseEntity<String> addMoment(@RequestBody Moment moment){
		userService.addMoment(moment);
		return ResponseEntity.ok("ok");
		
	}

	@GetMapping("/moment")
	public ResponseEntity<List<Moment>> getMoments(@RequestParam int pageSize){
		return ResponseEntity.ok(userService.getMoments(pageSize));
	}
	
	@GetMapping("/maxPage")
	public ResponseEntity<String> getMaxPage() {
		return ResponseEntity.ok(userService.getMaxPage());
	}
}
