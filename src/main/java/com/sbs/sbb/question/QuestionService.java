package com.sbs.sbb.question;

import com.sbs.sbb.DataNoFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    // questionRepository 연결
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        // questionRepository를 통해 질문 목록을 가져옴
        return this.questionRepository.findAll();
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

    public void create(String subject, String content){
        Question q = new Question(); // 제목과 내용으로 새로운 Question 객체 생성
        q.setSubject(subject); // 새로운 제목 생성
        q.setContent(content); // 새로운 내용 생성
        q.setCreateDate(LocalDateTime.now()); // 현재시간을 작성날짜 설정
        this.questionRepository.save(q); // 새로운 questijon객체를 저장
    }

}
