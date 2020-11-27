package fudan.homework.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping(value="/{pageId}")
	public String defaultMapping(@PathVariable String pageId) {
		return pageId;
	}
	
}
