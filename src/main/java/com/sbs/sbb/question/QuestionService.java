package com.sbs.sbb.question;

import com.sbs.sbb.DataNoFoundException;
import com.sbs.sbb.answer.Answer;
import com.sbs.sbb.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    private Specification<Question> search(String kw){
        // kw라는 키워드를 기반으로 동적 쿼리 생성하는 search  메서드 생성

        return new Specification<Question>() {
            // Specification을 구현하는 익명 클레스 생성
            private static final long serialVersionUID = 1L;
            // 직렬화를 위한 serialVersionUID  정의
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복결과 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                // Question SiteUser  Entity Join
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT );
                // Question Answer Entity Join
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                // Answer SiteUser Entity Join
                return cb.or(cb.like(q.get("subject"), "%"+ kw + "%"),
                        cb.like(q.get("content"), "%" + kw + "%"),
                        cb.like(u1.get("username"), "%" + kw + "%"),
                        cb.like(a.get("content"), "%" + kw + "%"),
                        cb.like(u2.get("username"), "%" + kw + "%"));
                // cb는 CriteriaBuilder 객체를 나타냄
                // JPA Criteria API의 일부로, 동적ㅈ인 쿼리를 작성하기 위해 사용
                // like메서드는 특정 필드에 대한 부분 문자열 매칭을 수행
                //  cb.like(Expression<String> x, String pattern)에서 x는 대상 필드, pattern은 비교할 패턴을 나타냄
                // cb.or는 OR연산을 수행하는 메서드
                // 주어진 모든 조건 중 하나라도 충족되면 해당 조건을 만족시키는 데이터 반환
            }
        };
    }

    public Page<Question> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        // Sort.Order 객체를 요소로 하는 새로운 리스트 생성
        sorts.add(Sort.Order.desc("createDate"));
        // createDate필드를 기준으로 내림차순으로 정렬하는 Sort.order 객체를 리스트에 추가
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        // sort.by(sorts)는 정렬 조건을 나타냄
        // Pageable 객체를 생성하여 페이지 번호와 페이지당 표시할 항목의 수 설정
        // 데이터 전체를 조회하지 않고 해당 페이지의 데이터만 조회 함
        Specification<Question> spec = search(kw);
        // search 메서드를 호출하여 kw에 따라 Specification 객체를 가져오고  그것을 spec변수에 할당함
        // 주어진 검색어를 기반으로 데이터베이스에서 질문을 필터링하는데 사용됨
        return this.questionRepository.findAll(spec,pageable);
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

    public void modify(Question question, String subject,String content){
        question.setSubject(subject); // question 객체의 제목을 매개변수 값으로 변경
        question.setContent(content); // 내용을 매개변수 값으로 변경
        question.setModifyDate(LocalDateTime.now()); // 수정날짜를 현재시간으로 변경
        this.questionRepository.save(question); // 객체에 저장
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }


}
