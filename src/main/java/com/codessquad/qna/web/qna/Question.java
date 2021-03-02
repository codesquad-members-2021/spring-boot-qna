package com.codessquad.qna.web.qna;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private Date reportingDate;
    private List<Comment> commentList;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.reportingDate = new Date();
        commentList = new ArrayList<>();
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

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    class Comment {
        private String commentId;
        private Date reportingDate;
        private String writer;
        private String contents;

        public Comment(String commentId, Date reportingDate, String writer, String contents) {
            this.commentId = commentId;
            this.reportingDate = reportingDate;
            this.writer = writer;
            this.contents = contents;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public Date getReportingDate() {
            return reportingDate;
        }

        public void setReportingDate(Date reportingDate) {
            this.reportingDate = reportingDate;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }
    }
}
