package com.sbs.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @Size(min = 3, max =25) // 길이 검증
    @NotEmpty(message = "사용자 ID는 필수 항목입니다") // 오류시 메시지 나옴
    private String username;

    @NotEmpty(message = "비밀번호는 필수 항목입니다")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다")
    private String password2;

    @Email // 이메일 속성과 맞는지 검증
    @NotEmpty(message = "사용자 ID는 필수 항목입니다")
    private String email;
    
}
