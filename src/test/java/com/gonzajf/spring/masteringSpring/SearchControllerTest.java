package com.gonzajf.spring.masteringSpring;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gonzajf.spring.masteringSpring.controller.SearchController;
import com.gonzajf.spring.masteringSpring.model.LightTweet;
import com.gonzajf.spring.masteringSpring.service.SearchService;;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
@WebAppConfiguration
public class SearchControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@MockBean
	private SearchService searchService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void should_search() throws Exception {
		
		given(searchService.search(anyString(), anyList()))
				.willReturn(Arrays.asList(new LightTweet("tweetText")));
		
		this.mockMvc.perform(get("/search/mixed;keywords=spring"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(view().name("resultPage"))
					.andExpect(model().attribute("tweets", everyItem(hasProperty("text", is("tweetText")))));
		
		verify(searchService, times(1)).search(anyString(), anyList());
	}

}
