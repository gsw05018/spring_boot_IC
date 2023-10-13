package com.sbs.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
