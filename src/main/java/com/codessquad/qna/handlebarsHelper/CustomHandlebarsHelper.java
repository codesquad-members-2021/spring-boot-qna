package com.codessquad.qna.handlebarsHelper;

import com.codessquad.qna.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class CustomHandlebarsHelper {

    private final Logger logger = LoggerFactory.getLogger(CustomHandlebarsHelper.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CharSequence formatDateTime(LocalDateTime time) {
        return time.format(dateTimeFormatter);
    }

    public CharSequence questionPagination(Page<Question> questions, int pageNumber) {
        logger.debug("pnum : " + pageNumber);
        logger.debug("total pnum : " + questions.getTotalPages());
        int PAGE = 5;
        int pageIndex = pageNumber - 1;
        int pageStart = (pageIndex / PAGE) * PAGE;
        int pageEnd = ((pageIndex / PAGE) + 1) * PAGE;
        boolean nextPages = true;
        if (pageEnd > questions.getTotalPages() - 1) {
            pageEnd = pageIndex + 1;
            nextPages = false;
        }
        StringBuilder pagination = new StringBuilder();
        if (pageStart != 0) {
            pagination.append("<li><a href=\"/questions?page=")
                    .append(pageStart)
                    .append("\">«</a></li>");
        }
        for (int i = pageStart; i < pageEnd; i++) {
            int pageNum = i + 1;
            pagination.append("<li><a href=\"/questions?page=")
                    .append(pageNum)
                    .append("\">")
                    .append(pageNum)
                    .append("</a></li>");
        }
        if (nextPages) {
            pagination.append("<li><a href=\"/questions?page=")
                    .append(pageEnd + 1)
                    .append("\">»</a></li>");
        }
        logger.debug(pagination.toString());
        return pagination.toString();
    }

}
