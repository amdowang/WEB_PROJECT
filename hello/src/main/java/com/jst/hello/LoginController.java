package com.jst.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		System.out.println("come login");
		return "hello";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String fail() {
		System.out.println("come fail");
		return "hello";
	}

	// 访问 /hello/login/greeting,/greeting1与greeting1一样的效果
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	// 访问 /hello/login/greeting1
	@RequestMapping("greeting1")
	public String greeting1(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	@RequestMapping("greeting2")
	public ModelAndView greeting2(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
		ModelAndView mv = new ModelAndView("greeting");
		mv.addObject("name", " Hello world ， test my first spring mvc ! ");
		return mv;
	}
}