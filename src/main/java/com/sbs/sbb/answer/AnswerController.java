package com.sbs.sbb.answer;

import com.sbs.sbb.question.Question;
import com.sbs.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 스프링 컨트루러임을 나타냄
@RequiredArgsConstructor // 롬북을 사용하여 생성자 주입을 위해 필요한 생성자를 자동으로 생성
@RequestMapping("/answer") // 기본경로 설정
public class AnswerController {

    private final QuestionService questionService; // QuestionService 객체를 주입받음

    @GetMapping("/create/{id}") // GET요청을 처리하는 createAnswer 메소드정의하고 경로변수 받음
    public String createAnswer(Model model, @PathVariable Integer id, @RequestParam String content){

        Question question = this.questionService.getQuestion(id); // id에 해당되는 질문목록을 가져옴

        return String.format("rediredct:/question/detail/%s", id); //해당 id에 대한 question/detail 엔드포인트로 리디렉션함

    }

}
