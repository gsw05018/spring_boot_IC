package com.sbs.sbb.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // JapRepository<엔티티 타입, pk 속성>

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

}
