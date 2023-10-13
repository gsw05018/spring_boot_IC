package com.sbs.sbb;

public class DataNoFoundException extends RuntimeException {
    // 데이터를 찾지 못하였을 때 발생하는 에외
    private static final long serialVersionUID = 1L;
    // 객체를 직렬화하고 역질렬화 할때 사용되는 버전 번호를 나타냄
    public DataNoFoundException(String message) {
        super(message);
    }
}
// 사용자 정의 예외 클래스를 정의함
// RuntimeException클래스 확장하며 주어진 메시지를 가지고 예외 생성
// 확인될 필요 x, 명시적인 예외 처리 x
