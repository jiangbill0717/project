package com.fudan.project.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

	/**
     * 登陆界面
     */
	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping(value="/{pageId}")
	public String defaultMapping(@PathVariable String pageId) {
		return pageId;
	}
	
    /**
     * 聊天界面
     */
    @GetMapping("/index")
    public ModelAndView index(String username, String password, HttpServletRequest request) throws UnknownHostException {
        if (StringUtils.isEmpty(username)) {
            username = "匿名用户";
        }
        ModelAndView mav = new ModelAndView("/chat");
        mav.addObject("username", username);
        mav.addObject("webSocketUrl", "ws://"+InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/chat");
        return mav;
    }
}
