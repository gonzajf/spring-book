package com.gonzajf.spring.masteringSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gonzajf.spring.masteringSpring.form.UserProfileSession;

@Controller
public class HomeController {

	private UserProfileSession profileSession;

	@Autowired
	public HomeController() {
		profileSession = new UserProfileSession();
	}
	
	@RequestMapping("/")
	public String home() {
		
		List<String> tastes = profileSession.getTastes();
		
		if (tastes.isEmpty()) {
			return "redirect:/profile";
		}
		return "redirect:/search/mixed;keywords=" + String.join(",", tastes);
	}
}