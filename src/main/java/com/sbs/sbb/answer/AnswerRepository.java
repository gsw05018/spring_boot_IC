package com.sbs.sbb.answer;

import com.sbs.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    // JapRepository<엔티티 타입, pk 속성>
}
