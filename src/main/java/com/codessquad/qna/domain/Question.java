package com.codessquad.qna.domain;


public class Question {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private String writeTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + id +
                ", writer='" + writer + '\'' +
                ", subject='" + title + '\'' +
                ", content='" + contents + '\'' +
                '}';
    }
}
