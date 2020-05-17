package com.gonzajf.spring.masteringSpring.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gonzajf.spring.masteringSpring.model.LightTweet;
import com.gonzajf.spring.masteringSpring.service.SearchService;

@RestController
@RequestMapping("/api")
public class SearchApiController {

	@Autowired
	private SearchService searchService;
	
	@GetMapping("/search/{searchType}")
	public List<LightTweet> searchTweets(@PathVariable String searchType, 
									@MatrixVariable List<String> keywords) {
		
		return searchService.search(searchType, keywords);
	}
	
}
