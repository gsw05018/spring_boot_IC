package com.sbs.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//spring 구성 클래스임을 알려줌
@EnableWebSecurity
// spring security 활성화 및 보안구성 제공함
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // httpSecurity 객체 사용하여 웹 보안 구성
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // http 요청에 대한 권한 부여
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                        // 모든 URL 패턴에 대해 권한을 permitaAll()로 설정

        ;
        return http.build();
    }
}