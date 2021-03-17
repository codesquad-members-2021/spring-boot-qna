package com.codessquad.qna.domain;


public class Question {

    private int questionId;
    private String writer;
    private String title;
    private String contents;
    private String writeTime;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", subject='" + title + '\'' +
                ", content='" + contents + '\'' +
                '}';
    }
}
