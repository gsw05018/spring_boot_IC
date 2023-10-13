package com.sbs.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
