package com.sbs.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody
    public String index(){
        return "다시 시작";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
        // 홈으로 이동시 리다이렉션하여 질문목록으로 이동시킴
    }

}
