package com.sbs.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    // 열겨형인 UserRole 정의
    // 프로그래머가 직접 정의한 상수 값의 집합을 나타내는 자바의 특별한 데이터
    // 프로그래밍을 간소화하고 가독성을 높이기 위해 사용
    // 여러개의 고정된 값을 사용해야 하는 경우에는 열거형을 사용
    // 코드를 보다 명확하게 구성 가능 유지보수 용이

    ADMIN("ROLE_ADMIN"), // ROLE_ADMIN 문자열 값에 매핑 > 시스템으로서는 이 역할에 대한 구분을 쉽게 가능
    USER("ROLE_USER"); // ROLE_USER 문자열 값에 매핑 > 시스템으로서는 이 역할에 대한 구분을 쉽게 가능

    UserRole(String value){
        this.value = value;
    }

    private String value;

}
