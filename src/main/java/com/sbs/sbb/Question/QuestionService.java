package com.sbs.sbb.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    // questionRepository 연결
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        // questionRepository를 통해 질문 목록을 가져옴
        return this.questionRepository.findAll();
    }

}
