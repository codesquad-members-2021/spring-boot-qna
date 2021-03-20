package com.codessquad.qna.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BaseDTO {
    private Long id;

    @JsonFormat(pattern = Constant.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime createDateTime;

    @JsonFormat(pattern = Constant.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime updateDateTime;

    protected BaseDTO() {
    }

    protected BaseDTO(Long id) {
        this.id = id;
    }

    protected BaseDTO(Long id, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.id = id;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
