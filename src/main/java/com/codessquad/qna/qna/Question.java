package com.codessquad.qna.qna;

public class Question {
    private String index;
    private String writer;
    private String title;
    private String contents;

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
