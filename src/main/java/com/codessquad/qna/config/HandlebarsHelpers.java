package com.codessquad.qna.config;

import com.codessquad.qna.domain.DisplayStatus;
import com.codessquad.qna.domain.Question;
import org.springframework.data.domain.Page;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.util.ArrayList;
import java.util.List;

@HandlebarsHelper
public class HandlebarsHelpers {
    public boolean checkStatus(final Object post) {
        return post == DisplayStatus.OPEN;
    }

    public boolean checkPageFirst(final Page<Question> page) {
        return !page.isFirst();
    }

    public boolean checkPageLast(final Page<Question> page) {
        return !page.isLast();
    }

    public List<Integer> checkPageNum(final Page<Question> page) {
        List<Integer> pages = new ArrayList<>();
        int count = 0;
        int pageNumber = page.getNumber();
        for (int i = pageNumber; i < page.getTotalPages(); i++) {
            pages.add(i);
            count++;
            if (count == 5) {
                break;
            }
        }
        return pages;
    }

    public int plusOne(Object page) {
        return (int) page + 1;
    }

}
