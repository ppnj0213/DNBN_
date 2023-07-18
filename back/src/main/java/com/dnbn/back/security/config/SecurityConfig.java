package com.dnbn.back.security.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dnbn.back.security.auth.MemberDetailsService;
import com.dnbn.back.security.handler.Http401Handler;
import com.dnbn.back.security.handler.Http403Handler;
import com.dnbn.back.security.handler.LoginFailHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/favicon.ico")
			.requestMatchers("/error")
			.requestMatchers(toH2Console());
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/api/members/login", "/api/members/signup").permitAll()
				.requestMatchers("/user").hasAnyRole("USER", "ADMIN") // 권한 여러개
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated() // 어떤 요청이라도 인증 필요
			)
			.logout(Customizer.withDefaults() // "/logout" 으로 인증 해제
				// (logout) -> logout
				// .logoutSuccessUrl("/login")
				// .invalidateHttpSession(true)
			)
			.formLogin(login -> login
				.usernameParameter("userId")
				.passwordParameter("userPw")
				.loginPage("/api/members/login") // 로그인 페이지
				.loginProcessingUrl("/api/members/login") // 실제 post로 값을 받아서 검증하는 주소
				.defaultSuccessUrl("/") // 로그인 성공 시 이동할 주소
				.failureHandler(new LoginFailHandler(new ObjectMapper()))
			)
			.rememberMe(rm -> rm
				.alwaysRemember(false)
				.rememberMeParameter("remember") // 로그인 시 파라미터로 remember 값이 넘어오면 세션이 만료되더라도 자동로그인
				.tokenValiditySeconds(2592000)
				.userDetailsService(userDetailsService())
			)
			.exceptionHandling(e -> {
				e.accessDeniedHandler(new Http403Handler());
				e.authenticationEntryPoint(new Http401Handler());
			})
			.userDetailsService(userDetailsService())
			.build();
	}

	@Bean
    public UserDetailsService userDetailsService() {
        return new MemberDetailsService();
    }

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
