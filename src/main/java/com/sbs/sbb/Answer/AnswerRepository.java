package com.sbs.sbb.Answer;

import com.sbs.sbb.Question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Question, Integer> {
    // JapRepository<엔티티 타입, pk 속성>
}