package com.sbs.sbb.answer;

import com.sbs.sbb.question.Question;
import com.sbs.sbb.question.QuestionService;
import com.sbs.sbb.user.SiteUser;
import com.sbs.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller // 스프링 컨트루러임을 나타냄
@RequiredArgsConstructor // 롬북을 사용하여 생성자 주입을 위해 필요한 생성자를 자동으로 생성
@RequestMapping("/answer") // 기본경로 설정
public class AnswerController {

    private final QuestionService questionService; // QuestionService 객체를 주입받음
    private final AnswerService answerService; // AnswerService 객체를 주입받음
    private final UserService userService;

    @PreAuthorize("isAuthenticated()") // login시만 이용 가능
    @PostMapping("/create/{id}") // Post요청을 처리하는 createAnswer 메소드정의하고 경로변수 받음
    public String createAnswer(Model model, @PathVariable Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal){
// Principal 객체를 사용해 사용자에 대한 정보 제공

        Question question = this.questionService.getQuestion(id); // id에 해당되는 질문목록을 가져옴
        SiteUser siteUser = this.userService.getUSer(principal.getName()); // principal 객체를 통해 사용자명을 가져옴

        if(bindingResult.hasErrors()){ // 바이딩결과에 오류 검증
            model.addAttribute("question", question); // 오류가 있을시에도 사용자가 입력한 데이터 유지
            return "question_detail"; // 오류가 있는 경우에도 데이터 유지하면서 페이지로 감
        }

        this.answerService.create(question, answerForm.getContent(), siteUser); // create 메서드를 호출할 때 question과 content를 전달하여 답변 생성 / siteuser얻어서 답변을 등록하는 AnswerService create메서드에 전달하여 답변 저장

        return String.format("redirect:/question/detail/%s", id); //해당 id에 대한 question/detail 엔드포인트로 리디렉션함

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal){
        Answer answer = this.answerService.getAnswer(id);
        // id를 통해 답변을 가져옴
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            // 현재 사용자와 답변 작성자가 맞느지 확인후 오류메시지 출현
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        answerForm.setContent(answer.getContent());
        // 답변 양식의 내용을 해당 답변의 내용으로 설정
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal){
        if(bindingResult.hasErrors()){
            return "answer_form";
            // form 유효성 검사
        }
        Answer answer = this.answerService.getAnswer(id);
        // id를 통해 답변을 가져옴
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            // 작성자가 맞는지 검증
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
            // 오류메시지 출현
        }
        this.answerService.modfy(answer, answerForm.getContent());
        // 주어진 양식으로 수정하고 저장
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        // 수정후 상세페이지로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id){
        Answer answer = this.answerService.getAnswer(id);
        // id를 통해 답변을 가져옴
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            // 작성자 검증
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 업습니다");
        }
        this.answerService.delete(answer);
        // 답변 삭제
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        // 삭제후 상세페이지 이동
    }

}
