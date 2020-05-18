package com.gonzajf.spring.masteringSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").defaultSuccessUrl("/profile").and().logout().logoutSuccessUrl("/login")
				.and().authorizeRequests().antMatchers("/webjars/**", "/login").permitAll().anyRequest()
				.authenticated();
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		firewall.setAllowSemicolon(true);
		return firewall;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	  super.configure(web);
	  web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	}
}