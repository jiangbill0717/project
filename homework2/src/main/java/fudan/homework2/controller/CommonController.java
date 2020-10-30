package fudan.homework2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}
	
	@GetMapping(value="/{pageId}")
	public ModelAndView defaultMapping(@PathVariable String pageId) {
		return new ModelAndView("pageId");
	}
	
}
