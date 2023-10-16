package com.sbs.sbb.question;

import com.sbs.sbb.DataNoFoundException;
import com.sbs.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    // questionRepository 연결
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        // Sort.Order 객체를 요소로 하는 새로운 리스트 생성
        sorts.add(Sort.Order.desc("createDate"));
        // createDate필드를 기준으로 내림차순으로 정렬하는 Sort.order 객체를 리스트에 추가
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        // sort.by(sorts)는 정렬 조건을 나타냄
        // Pageable 객체를 생성하여 페이지 번호와 페이지당 표시할 항목의 수 설정
        // 데이터 전체를 조회하지 않고 해당 페이지의 데이터만 조회 함
        return this.questionRepository.findAll(pageable);
        // findAll 메서드를 호출하여 페이지 요청에 따라 해당 페이지의 질문 목록을 가져옴
    }
    public Question getQuestion(Integer id){

        Optional<Question> question = this.questionRepository.findById(id);
        // 주어진 id에 해당하는 질문을 데이터베이스에서 찾기 위해 questionRepository호출, 값이 존재하지 않을 수도 있음
        if(question.isPresent()){
            // question이 존재하는지 검사
            return question.get();
            // 있다면 질문 반환
        } else {
            throw new DataNoFoundException("question not found");
            // 없다면 오류메시지 출력
        }

    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question(); // 제목과 내용으로 새로운 Question 객체 생성
        q.setSubject(subject); // 새로운 제목 생성
        q.setContent(content); // 새로운 내용 생성
        q.setCreateDate(LocalDateTime.now()); // 현재시간을 작성날짜 설정
        q.setAuthor(user);
        this.questionRepository.save(q); // 새로운 questijon객체를 저장
    }



}
