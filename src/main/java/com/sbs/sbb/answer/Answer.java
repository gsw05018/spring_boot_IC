package com.sbs.sbb.answer;

import com.sbs.sbb.question.Question;
import com.sbs.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity // 엔티티임을 알려줌
public class Answer {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private Integer id;

    @Column(columnDefinition = "TEXT") // 타입
    private String content;

    // 시간
    private LocalDateTime createDate;


    @ManyToOne // 외래키 관계, question_id가 생김
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;
    // 수정날짜

    @ManyToMany
    Set<SiteUser> voter; // 추천인,. set으로 한 이유는 중복을 허용하지 않는 자료형이다
}
