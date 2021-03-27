package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDateTime modifiedDateTime;

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }
}
