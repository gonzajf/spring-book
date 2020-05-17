package com.gonzajf.spring.masteringSpring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.gonzajf.spring.masteringSpring.model.LightTweet;

@Service
public class SearchService {

	@Autowired
	private Twitter twitter;

	public List<LightTweet> search(String searchType, List<String> keywords) {
		List<SearchParameters> searches = keywords
											.stream()
											.map(taste -> createSearchParam(searchType, taste))
											.collect(Collectors.toList());
		
		List<LightTweet> results = searches
								.stream()
								.map(params -> twitter.searchOperations().search(params))
								.flatMap(searchResults -> searchResults.getTweets().stream())
								.map(LightTweet::ofTweet)
								.collect(Collectors.toList());
		return results;
	}

	private SearchParameters.ResultType getResultType(String searchType) {
		for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {
			if (knownType.name().equalsIgnoreCase(searchType)) {
				return knownType;
			}
		}
		return SearchParameters.ResultType.RECENT;
	}

	private SearchParameters createSearchParam(String searchType, String taste) {
		SearchParameters.ResultType resultType = getResultType(searchType);
		SearchParameters searchParameters = new SearchParameters(taste);
		searchParameters.resultType(resultType);
		searchParameters.count(3);
		return searchParameters;
	}
}