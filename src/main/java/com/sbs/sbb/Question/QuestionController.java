package com.sbs.sbb.Question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {

    @GetMapping("/question/list")
    public String list(){
        return "Question_list";
    // html을 출력하기 위해서는 Reposbody를 지우고 getmapping만 사용해 링크를 연결해준다
    }

}
