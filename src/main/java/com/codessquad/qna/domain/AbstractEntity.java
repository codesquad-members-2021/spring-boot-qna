package com.codessquad.qna.domain;

import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.util.DateTimeUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void checkSameUser(Long newId) {
        if (this.id != newId) {
            throw new IllegalUserAccessException("자신의 정보만 수정 가능");
        }
    }

    public String getFormatCreateDateTime() {
        return createDateTime.format(DateTimeUtils.dateTimeFormatter);
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }
}
