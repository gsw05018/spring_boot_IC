package com.sbs.sbb.Question;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
