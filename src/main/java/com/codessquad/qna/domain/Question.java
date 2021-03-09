package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String writer;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 20)
    private String contents;
    @Column(nullable = false, length = 20)
    // ZonedDateTime 타임존 또는 시차 개념이 필요한 날짜와 시간 정보를 나타낼 때 사용: https://www.daleseo.com/java8-zoned-date-time/
    // 배포서버가 미국에 있기 때문에 타임존을 사용하였습니다.
    private final String time = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id=" + id +
                ", time=" + time +
                '}';
    }
}
