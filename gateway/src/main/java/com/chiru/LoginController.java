package com.chiru;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) throws ServletException
	{
		request.logout();
		return "redirect:/";
	}
}
