package com.sbs.sbb.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor // loombok 라이브러리로 final에 대해 자동으로 생성자 생성
@Controller
public class QuestionController {

    // questionRepository 연결
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public String list(Model model){

        // questionRepositroy에서 모든 질문 목록을 가져옴
        List<Question> questionList = this.questionRepository.findAll();

        // model에서 questionlist를 추가하여 view에서 사용할 수 있게 함
        model.addAttribute("questionList", questionList);

        return "question_list";
    // html을 출력하기 위해서는 Reposbody를 지우고 getmapping만 사용해 링크를 연결해줌
    // question_list라는 이름의 템플릿을 렌더링하여 반환

    }

}
