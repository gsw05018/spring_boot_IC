package com.sbs.sbb.question;

import com.sbs.sbb.answer.AnswerForm;
import com.sbs.sbb.user.SiteUser;
import com.sbs.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question") // question 생략 가능
@RequiredArgsConstructor // loombok 라이브러리로 final에 대해 자동으로 생성자 생성
@Controller
public class QuestionController {

    // questionService 연결
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0")int page){

        Page<Question> paging = this.questionService.getList(page);
        // 페이지 번호를 기반으로 questionService의 getList메서드를 호출하여 페이지별 질문 목록을 가져옴
        model.addAttribute("paging", paging);
        // 모델에 페이징 정보 저장

        return "question_list";
    // html을 출력하기 위해서는 Reposbody를 지우고 getmapping만 사용해 링크를 연결해줌
    // question_list라는 이름의 템플릿을 렌더링하여 반환

    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {

        Question question = this.questionService.getQuestion(id);
        // 서비스에서 id를 이용해 질문을 불러옴
        model.addAttribute("question", question);
        // question이름으로 model에 추가됨
        return "question_detail";
        // question_detail 뷰를 반환
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    // 질문등록하기 눌렀을 때 메서드
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create") // POST방식으로 http 요청 처리
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
        // QuestionForm 객체로 변경, subject content항목을 지닌 폼이 전송되면 QuestionForm의 subject, content속성이 자동으로 바인딩됨
        // @Valid 애너테이션이 적용이 되면 검증 기능이 독장함
        // BindingResult는 검증이 수행된 결과를 의미하는 객체임

        if(bindingResult.hasErrors()){ // 오류가 있는 경우 폼작성 화면 반환
            return "question_form";
        }

        SiteUser siteUser = this.userService.getUSer(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
       // create메서드를 호출하여 질문 생성
        return "redirect:/question/list"; // 질문등록후 목록으로 이동
        // 질문 등록 홈페이지 메서드
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
        Question question = this.questionService.getQuestion(id);
        // id에 해당되는 질문을 가져옴
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            // 현재 사용자와 질문 작성가 같는지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
            // 같지 않을 시 오류메시지 던짐
        }
        questionForm.setSubject(question.getSubject());
        // 질문의 제목을 폼에 설정
        questionForm.setContent(question.getContent());
        // 질문의 내용을 폼에 설정
        return "question_form";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal){
        if(bindingResult.hasErrors()){
            return "question_form";
            // 오류가 날시 다시 돌아감
        }
        Question question = this.questionService.getQuestion(id);
        // 수정하려는 질문의 정보를 가져옴
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            // 현재사용자와 질문사용자가 맞는지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다");
            // 맞지 않을시 오류메시지 출현
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        // 질문의 제목과 내용을 수정된 양식으로 변경, 수정날짜도 현재날짜로 변경
        return String.format("redirect:/question/detail/%s", id);
        // 수정된 질문의 상세페이지로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        // id를 통해 질문을 가져옴
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
            // 삭제할 사용자와 질문사용자 같는지 확인후 오류메시지 출현
        }
        this.questionService.delete(question); // 질문삭제
        return "redirect:/"; // 메인페이지로 이동
    }
}
