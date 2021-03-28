package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @JsonProperty("createdDateTime")
    public String getFormattedCreatedDateTime() {
        return getFormattedDateTime(createdDateTime);
    }

    @JsonProperty("modifiedDateTime")
    public String getFormattedModifitedDateTime() {
        return getFormattedDateTime(modifiedDateTime);
    }

    private String getFormattedDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DATE_TIME_FORMAT);
    }

    public boolean matchesId(Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
