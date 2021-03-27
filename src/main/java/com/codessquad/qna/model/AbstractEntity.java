package com.codessquad.qna.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Date createDate;

    @LastModifiedDate
    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public String getCreateDate() {
        return formatDate(this.createDate, "yyyy-MM-dd HH:mm");
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return formatDate(this.modifiedDate, "yyyy-MM-dd HH:mm");
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", " +
                "createDate: " + this.createDate + ", " +
                "modifiedDate: " + this.modifiedDate;
    }

}
