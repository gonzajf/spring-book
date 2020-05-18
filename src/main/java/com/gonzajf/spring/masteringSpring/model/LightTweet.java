package com.gonzajf.spring.masteringSpring.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

public class LightTweet {
	
	private String profileImageUrl;
	private String user;
	private String text;
	private LocalDateTime date;
	private String lang;
	private Integer retweetCount;

	public LightTweet(String t) {
		text = t;
	}
	
	public static LightTweet ofTweet(Tweet tweet) {
		
		LightTweet lightTweet = new LightTweet(tweet.getText());
		
		Date createdAt = tweet.getCreatedAt();
		if(createdAt != null) {
			lightTweet.setDate(LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault()));
		}
		
		TwitterProfile tweetUser = tweet.getUser();
		if(tweetUser != null) {
			lightTweet.setUser(tweetUser.getName());
			lightTweet.setProfileImageUrl(tweetUser.getProfileImageUrl());
		}
		lightTweet.setLang(tweet.getLanguageCode());
		lightTweet.setRetweetCount(tweet.getRetweetCount());
		return lightTweet;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Integer getRetweetCount() {
		return retweetCount;
	}
	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}
	
	@Override
	public String toString() {
		return "LightTweet [profileImageUrl=" + profileImageUrl + ", user=" + user + ", text=" + text + ", date=" + date
				+ ", lang=" + lang + ", retweetCount=" + retweetCount + "]";
	}
}