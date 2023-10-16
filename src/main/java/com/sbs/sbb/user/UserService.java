package com.sbs.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 사용자 저장소
    private final UserRepository userRepository;

    public SiteUser create(String username, String email, String password){
    // 사용자 생성 메서드

        SiteUser user = new SiteUser();
        // user 객체 생성
        user.setUsername(username);
        // 사용자명 설정
        user.setEmail(email);
        // 이메일 설정
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 비밀번호 암호화를 위한 BCryptPasswordRncoder 객체 생성
        user.setPassword(passwordEncoder.encode(password));
        // 비밀번호 설정 및 암호화
        this.userRepository.save(user);
        // user 정보 repository에 저장
        return user;
        // user를 반환
    }

}
