package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = true)
    @CreatedDate
    private LocalDateTime createdDateTime;

    @Column(nullable = true)
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    public Long getId() {
        return id;
    }

    public String getFormattedCreatedDateTime() {
        return getFormattedDateTime(createdDateTime);
    }

    public String getFormattedModifiedDateTime() {
        return getFormattedDateTime(modifiedDateTime);
    }

    private String getFormattedDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(dateTimeFormatter);
    }
}
