package com.gonzajf.spring.masteringSpring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TweetController {
	
	@Autowired
	private Twitter twitter;
	
	@RequestMapping("/")
	public String home() {
		return "searchPage";
	}
	
	@RequestMapping("/result")
	public String result(@RequestParam(defaultValue = "java") String search, Model model) {

		SearchResults searchResults = twitter.searchOperations().search(search);
		List<Tweet> tweets = searchResults.getTweets();
		model.addAttribute("tweets", tweets);
		model.addAttribute("search", search);	
		
		return "resultPage";
	}
	
	@RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	public RedirectView postSearch(HttpServletRequest request, RedirectAttributes redirectAttrs) {
		
		String search = request.getParameter("search");
		
		if(search.toLowerCase().contains("struts")) {
			redirectAttrs.addFlashAttribute("error", "Try using Spring instead!");
			return new RedirectView("/");
		}
		
		redirectAttrs.addAttribute("search", search);
		
		return new RedirectView("/result");
	}
}