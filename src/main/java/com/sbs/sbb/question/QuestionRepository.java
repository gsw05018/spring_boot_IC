package com.sbs.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // JapRepository<엔티티 타입, pk 속성>

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);
    // Pageable 객체를 입력으로 받아 Page<question>타입 객체를 리턴하는 findAll 메서드 생성

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
    // 데이터르 필터링하기 위해 사용되는 JPA Specification 객체
    // 데이터를 쿼리하고원하는 조건에 따라 데이터를 가져옴
    // 데이터를 페이지 단위로 가져오기
}
