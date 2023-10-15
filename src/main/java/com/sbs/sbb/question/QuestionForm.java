package com.sbs.sbb.question;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuestionForm {

    @NotEmpty(message = "제목은 필수항목입니다")
    // 빈 문자열이거나 null일 수 없음을 나ㅏ냄
    @Size(max=200)
    // 허용하는 최대길이
    private String subject;


    @NotEmpty(message = "내용은 필수항목입니다")
    // 빈 문자열이거나 null일 수 없음을 나ㅏ냄
    private String content;

}
