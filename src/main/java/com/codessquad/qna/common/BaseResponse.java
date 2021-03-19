package com.codessquad.qna.common;

import java.time.LocalDateTime;

import static com.codessquad.qna.common.DateUtils.format;

public abstract class BaseResponse {
    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    protected BaseResponse(Builder builder) {
        this.id = builder.id;
        this.createdDateTime = builder.createdDateTime;
        this.modifiedDateTime = builder.modifiedDateTime;
    }

    protected abstract static class Builder<T extends Builder<T>> {
        private Long id;
        private LocalDateTime createdDateTime;
        private LocalDateTime modifiedDateTime;

        protected abstract T self();

        protected abstract BaseResponse build();

        public T id(Long id) {
            this.id = id;
            return self();
        }

        public T createdDate(LocalDateTime createdDateTime) {
            this.createdDateTime = createdDateTime;
            return self();
        }

        public T modifiedDate(LocalDateTime modifiedDateTime) {
            this.modifiedDateTime = modifiedDateTime;
            return self();
        }
    }

    public Long getId() {
        return id;
    }

    public String getFormattedCreatedDateTime() {
        return format(createdDateTime);
    }
}
