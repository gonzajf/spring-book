package com.gonzajf.spring.masteringSpring;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gonzajf.spring.masteringSpring.api.UserApiController;
import com.gonzajf.spring.masteringSpring.model.User;
import com.gonzajf.spring.masteringSpring.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class UserApiControlllerAuthTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FilterChainProxy springSecurityFilter;

	@MockBean
	private UserRepository userRepository;

	private List<User> users = new ArrayList<>();
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
					.webAppContextSetup(wac)
					.addFilter(springSecurityFilter)
					.build();
		
		users.add(new User("mail@mail.io"));
		given(userRepository.findAll()).willReturn(users);

	}

	@Test
	public void unauthenticated_cannot_list_users() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
					.get("/api/users")
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isUnauthorized());
	}

	@Test
	public void admin_can_list_users() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
							.get("/api/users")
							.accept(MediaType.APPLICATION_JSON)
							.header("Authorization", basicAuth("admin", "admin")))
							.andDo(print())
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.[0].email").value("mail@mail.io"));

	}

	private String basicAuth(String login, String password) {
		byte[] auth = (login + ":" + password).getBytes();
		return "Basic " + Base64.getEncoder().encodeToString(auth);
	}
}
