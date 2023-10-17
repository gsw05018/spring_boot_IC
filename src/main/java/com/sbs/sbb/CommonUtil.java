package com.sbs.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component // 스프링에 관리되는 빈으로 등록
public class CommonUtil {
    public String markdown(String markdown) { // 주어진 문자열 Markdown 문자열을 HTML로 변환
        Parser parser = Parser.builder().build(); // 주어진 문자열을 파싱하여 Node 객체를 만듬
        Node document = parser.parse(markdown); // 파싱된  Node르 HTML로 렌더링하여 해당 HTMl문자열반환
        HtmlRenderer renderer = HtmlRenderer.builder().build(); 
        return renderer.render(document); // 변화딘 HTML 문자열 반환
    }
}