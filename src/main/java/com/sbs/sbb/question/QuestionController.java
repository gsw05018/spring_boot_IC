package com.sbs.sbb.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question") // question 생략 가능
@RequiredArgsConstructor // loombok 라이브러리로 final에 대해 자동으로 생성자 생성
@Controller
public class QuestionController {

    // questionService 연결
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model){

        // questionService를 통해 질문 목록을 가져옴
        List<Question> questionList = this.questionService.getList();

        // model에서 questionlist를 추가하여 view에서 사용할 수 있게 함
        model.addAttribute("questionList", questionList);

        return "question_list";
    // html을 출력하기 위해서는 Reposbody를 지우고 getmapping만 사용해 링크를 연결해줌
    // question_list라는 이름의 템플릿을 렌더링하여 반환

    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {

        Question question = this.questionService.getQuestion(id);
        // 서비스에서 id를 이용해 질문을 불러옴
        model.addAttribute("question", question);
        // question이름으로 model에 추가됨
        return "question_detail";
        // question_detail 뷰를 반환
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    // 질문등록하기 눌렀을 때 메서드
    }

    @PostMapping("/create") // POST방식으로 http 요청 처리
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
        // QuestionForm 객체로 변경, subject content항목을 지닌 폼이 전송되면 QuestionForm의 subject, content속성이 자동으로 바인딩됨
        // @Valid 애너테이션이 적용이 되면 검증 기능이 독장함
        // BindingResult는 검증이 수행된 결과를 의미하는 객체임

        if(bindingResult.hasErrors()){ // 오류가 있는 경우 폼작성 화면 반환
            return "question_form";
        }

        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
       // create메서드를 호출하여 질문 생성
        return "redirect:/question/list"; // 질문등록후 목록으로 이동
        // 질문 등록 홈페이지 메서드
    }


}
