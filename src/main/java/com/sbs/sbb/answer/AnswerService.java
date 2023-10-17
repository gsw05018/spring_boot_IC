package com.sbs.sbb.answer;

import com.sbs.sbb.DataNoFoundException;
import com.sbs.sbb.question.Question;
import com.sbs.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service // 스프링 서비스 선언
@RequiredArgsConstructor // 생성자 자동 생성
public class AnswerService {

    private final AnswerRepository answerRepository;
    // answerRepository 객체를 받음

    public void create(Question question, String content, SiteUser author){ // question 객체와 content를 매개변수로 받음

        Answer answer = new Answer(); // 새로운 Answer 객체 받음
        answer.setContent(content); // 답변 내용 설정
        answer.setQuestion(question); // 질문 설정
        answer.setCreateDate(LocalDateTime.now()); // 현자 날짜와 시간을 생성일로 설정
        answer.setAuthor(author); // 답변 저장시 작성자 저장
        this.answerRepository.save(answer); // 답변 저장

    }

    public Answer getAnswer(Integer id){
        // id를 기반으로 답변으로 찾음
        Optional<Answer> answer = this.answerRepository.findById(id);
        if(answer.isPresent()){
            // 답변이 존재하면 답변 반환
            return answer.get();
        } else {
            // 존재하지 않으면 오류 메시지 출현
            throw new DataNoFoundException("answer not found");
        }
    }

    public void modfy(Answer answer, String contnet){
        answer.setContent(contnet);
        // 답변내용을 주어진 내용으로 수정
        answer.setModifyDate(LocalDateTime.now());
        // 수정날짜를 현재 날짜로 변경
        this.answerRepository.save(answer);
        // 답변수정 저장
    }

    public void delete(Answer answer){
        this.answerRepository.delete(answer);
    }

}
