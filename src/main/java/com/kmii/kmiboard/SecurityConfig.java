package com.kmii.kmiboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  // 스프링부트의 환경설정 파일이라는 것을 명시하는 annotation
@EnableWebSecurity  // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 annotation 
@EnableMethodSecurity(prePostEnabled = true)  // 메서드에 권한 설정 - 인증받은 유저만 해당 메서드 가능하게 / @PreAuthorize 사용을 위해 설정 -> 로그인해야(인증받아야) 메서드 실행
public class SecurityConfig {
	
	@Bean
	   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	      http
	      	//인증처리 -> 로그인 하지 않고 바로 페이지 접근 가능
	         .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
	               .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//	         .csrf((csrf) -> csrf  //H2 DB 접속 콘솔 허가
//	        		 .ignoringRequestMatchers(new AntPathRequestMatcher("h2-console/**")))
	         .formLogin((formLogin) -> formLogin  // 스프링 시큐리티 로그인 설정
	        		 .loginPage("/user/login") //로그인 요청페이지 인식->스프링 시큐리티 /user/login->로그인 요청
	        		 .defaultSuccessUrl("/")) //로그인 성공시 이동할 페이지 -> 루트로 지정
	         .logout((logout)->logout
	        		 .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))  //로그아웃 설정
	         		 .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 페이지 설정
	         		 .invalidateHttpSession(true)) //세션 삭제
	         ;
	      return http.build();
	   }
	
	@Bean  // 비밀번호 암호화 객체
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean  // 스프링 시큐리티에서 인증을 처리하는 매니저 클래스
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
	throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
