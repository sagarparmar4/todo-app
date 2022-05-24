package com.deloitte.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.deloitte.demo.config.ApplicationConfig;
import com.deloitte.demo.services.UserDetailsServiceImpl;

/**
 * Configuration class for basic user authentication and authorization using
 * BCryptPasswordEncoder
 * 
 * TODO: Update configuration to consider HTML form login
 * 
 * @author Sagar Parmar
 *
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ApplicationConfig appConfig;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		HttpBasicConfigurer<HttpSecurity> httpBasicConfigurer = http
				.authorizeRequests()
				.antMatchers("/public").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
				.and().httpBasic();
		// @formatter:on

		// Disable CSRF for DEV environments
		if (appConfig.getSecurity().isCsrfEnabled()) {
			http = httpBasicConfigurer.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.and();
		} else {
			http = httpBasicConfigurer.and().csrf().disable();
		}
	}

}
