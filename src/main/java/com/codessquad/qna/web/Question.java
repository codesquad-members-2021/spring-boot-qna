package com.codessquad.qna.web;

public class Question {
    private String writer;
    private String title;
    //spring에서는 contents를 뭘로 받나
    private String contents;

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
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

    public String getContents() {
        return contents;
    }
}
