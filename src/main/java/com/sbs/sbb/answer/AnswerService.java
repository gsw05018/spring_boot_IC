package com.sbs.sbb.answer;

import com.sbs.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // 스프링 서비스 선언
@RequiredArgsConstructor // 생성자 자동 생성
public class AnswerService {

    private final AnswerRepository answerRepository;
    // answerRepository 객체를 받음

    public void create(Question question, String content){ // question 객체와 content를 매개변수로 받음

        Answer answer = new Answer(); // 새로운 Answer 객체 받음
        answer.setContent(content); // 답변 내용 설정
        answer.setQuestion(question); // 질문 설정
        answer.setCreateDate(LocalDateTime.now()); // 현자 날짜와 시간을 생성일로 설정
        this.answerRepository.save(answer); // 답변 저장

    }

}
