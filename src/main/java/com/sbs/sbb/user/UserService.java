package com.sbs.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 사용자 저장소
    private final UserRepository userRepository;
    // PasswordEncoder 을 불러옴
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password){
    // 사용자 생성 메서드

        SiteUser user = new SiteUser();
        // user 객체 생성
        user.setUsername(username);
        // 사용자명 설정
        user.setEmail(email);
        // 이메일 설정
        user.setPassword(passwordEncoder.encode(password));
        // 비밀번호 설정 및 암호화
        this.userRepository.save(user);
        // user 정보 repository에 저장
        return user;
        // user를 반환
    }

    // BCrypasswordEncoder 객체를 직접 new로 생성하는 방식보다는 been으로 등록해서 사용하는 것이 좋음
    // 암호화 방식을 변경하면 BCrypasswordEncoder를 사용한 모든 프로그램을 일일이 찾아서 수정해야 하기 때문
    // SecutityConfig에 @Been 메서드 생성하는것이 제일 좋은 방법

}
