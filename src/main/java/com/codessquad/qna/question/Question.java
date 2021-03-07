package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createDateTime = LocalDateTime.now();

    public Question() {
    }

    public Question(String writer, String title, String contents, LocalDateTime createDateTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDateTime = createDateTime;
    }

    public static List<Question> getDummyData() {
        return Arrays.asList(
                new Question("자바지기",
                        "국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        "test contents",
                        LocalDateTime.of(2016, 01, 15, 18, 47)),
                new Question("김문수",
                        "runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?",
                        "test contents",
                        LocalDateTime.of(2016, 01, 05, 18, 47)),
                new Question("김문수",
                        "InitializingBean implements afterPropertiesSet() 호출되지 않는 문제.\n",
                        "A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.\n" +
                                "서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.",
                        LocalDateTime.of(2015, 12, 30, 01, 47))
        );
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDateTime=" + createDateTime +
                '}';
    }
}
