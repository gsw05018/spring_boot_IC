package com.sbs.sbb.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 컨트룰러 역할을 한다는 것을 나타냄
@RequiredArgsConstructor // 생성자 자동 생성
@RequestMapping("/user") // 기본 경로 user 설정
public class UserController {

    private final UserService userService; // UserSrevice를 자동으로 연결하여 사용자 관련 작업 처리

    @GetMapping("/signup") // 사용자 등록을 위한 Get요청 처리
    public String signup(UserCreateForm userCreateForm){
        return "signup_form"; // signup_form 템플릿으로 렌더링
    }

    @PostMapping("/signup") // POST 매핑 처리
    public String singup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){ 
        if(bindingResult.hasErrors()){ // 유효성 검사하여 오류 확인
            return "signup_form"; // 있는 경우 signup_form 반환
        }

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){ // 비밀번호가 일치하는지 검증
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 스워드가 일치하지 않습니다");
            // 오류가 있을시 오류메시지 출현
            return "signup_form"; // 있을시 signup_form 렌더딩
        }
        userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1()); // userService를 호출하여 새 사용자 생성
        return "redirect:/"; // 성공을 했을 시 페이지 리디렉션 홈으로
    }

}
