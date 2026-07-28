package com.simple_shop.user_service.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/*
 * TODO Disables Spring security enough to access the endpoints. Just for until
 * I have the authorization service. When I have that, reconfigure Spring
 * security to work nicely.
 */
@Configuration
public class OpenSecurityConfig {
	public OpenSecurityConfig() {
		super();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		http
				// 1. Disable Cross-Site Request Forgery (CSRF)
				// This stops Spring from blocking Angular's POST and PUT payloads
				.csrf(CsrfConfigurer::disable)

				// 2. Open up all pathways globally
				// This guarantees that ANY HTTP method to ANY route returns raw data
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

		return http.build();
	}
}
