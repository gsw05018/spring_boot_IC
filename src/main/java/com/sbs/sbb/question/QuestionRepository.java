package com.sbs.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // JapRepository<엔티티 타입, pk 속성>

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    Page<Question> findAll(Pageable pageable);
    // Pageable 객체를 입력으로 받아 Page<question>타입 객체를 리턴하는 findAll 메서드 생성
}
