package com.codessquad.qna.domain;

import com.codessquad.qna.exception.NotAuthorizationException;
import com.codessquad.qna.utils.DateFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    public boolean isYourId(Long id) {
        if (!this.id.equals(id)) {
            throw new NotAuthorizationException();
        }
        return true;
    }

    public String getFormattedCreateDateTime() {
        if (createDateTime == null) {
            return "";
        }
        return createDateTime.format(DateFormat.DEFAULT);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTimeEntity that = (BaseTimeEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
