package com.codessquad.qna.module;

import com.codessquad.qna.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageFormatter {

    public static final int POSTS_PER_PAGE = 15;

    private final Pageable pageable;
    private final int previousPage;
    private final int nextPage;
    private final int totalPageCount;

    public PageFormatter(Pageable pageable, Page<Post> postPage, int totalPageCount) {
        this.pageable = pageable;
        this.previousPage = pageable.previousOrFirst().getPageNumber();
        this.totalPageCount = totalPageCount;
        if (pageable.next().getPageNumber() <= getCalcPageCount()) {
            this.nextPage = pageable.next().getPageNumber();
        } else {
            this.nextPage = pageable.getPageNumber();
        }
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public List<Integer> getPageNumbers() {
        return paging(pageable);
    }

    private List<Integer> paging(Pageable pageable) {
        PageBlock pageBlock = new PageBlock(getCalcPageCount());
        pageBlock.createPageBlocks(pageable.getPageNumber());
        return pageBlock.getBlocks();
    }

    private int getCalcPageCount() {
        return totalPageCount / POSTS_PER_PAGE;
    }

}
