package com.gonzajf.spring.masteringSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gonzajf.spring.masteringSpring.model.LightTweet;
import com.gonzajf.spring.masteringSpring.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search/{searchType}")
	public ModelAndView search(@PathVariable String searchType,
								@MatrixVariable List<String> keywords) {
		
		List<LightTweet> tweets = searchService.search(searchType, keywords);
		ModelAndView modelAndView = new ModelAndView("resultPage");
		modelAndView.addObject("tweets", tweets);
		modelAndView.addObject("search", String.join(",", keywords));
		return modelAndView;
	}
	
}
