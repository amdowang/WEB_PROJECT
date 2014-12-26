package com.jst.ssh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@RequestMapping(method = RequestMethod.POST)
	public String login() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}


}
