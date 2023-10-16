package com.sbs.sbb;

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

@Configuration
//spring 구성 클래스임을 알려줌
@EnableWebSecurity
// spring security 활성화 및 보안구성 제공함
@EnableMethodSecurity(prePostEnabled = true)
// 메서드 수준 보안 활성화
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // httpSecurity 객체 사용하여 웹 보안 구성
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // http 요청에 대한 권한 부여
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                        // 모든 URL 패턴에 대해 권한을 permitaAll()로 설정
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login") // 사용자가 로그인을 하지 않았을 때 리디렉션되는 기본 로그인 URL정의
                        .defaultSuccessUrl("/")) // 사용자가 로그인을 완료후 이동되는 URL
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))

        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    // 스프링 시큐리티의 인증을 관리하기 위해 사용됨
            throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // authenticationConfiguration.getAuthenticationManager(); 호출하여 인증 매니저를 가지고옴.

}