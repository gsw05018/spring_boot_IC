package com.sbs.sbb.question;

import com.sbs.sbb.answer.Answer;
import com.sbs.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity // 엔티티임을 알려줌
public class Question {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private Integer id;

    @Column(length = 200) // 글자수 제한
    private String subject;

    @Column(columnDefinition = "TEXT") // 타입
    private String content;

    // 시간
    private LocalDateTime createDate;

    // 외래키 관계 question을 참조 질문이 삭제되면 답변도 삭제
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;
}
