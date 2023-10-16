package com.sbs.sbb;

import com.sbs.sbb.question.Question;
import com.sbs.sbb.question.QuestionRepository;
import com.sbs.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void testJpa() {

		for(int i=0; i<300; i++){
			String subject = String.format("테스트 데이터[%s]", i);
			String content = "내용";
			this.questionService.create(subject, content);
		}


	}
}