package com.codessquad.qna.web.domain;

import java.util.List;

public class PageList {
    public static final long NO_PAGE = -1;
    private final List<Long> pages;
    private final long endPageOfPrevBlock;
    private final long startPageOfNextBlock;

    public PageList(List<Long> pages, long endPageOfPrevBlock, long startPageOfNextBlock) {
        this.pages = pages;
        this.endPageOfPrevBlock = endPageOfPrevBlock;
        this.startPageOfNextBlock = startPageOfNextBlock;
    }

    public boolean hasPrevBlock(){
        return endPageOfPrevBlock != NO_PAGE;
    }

    public boolean hasNextBlock(){
        return startPageOfNextBlock != NO_PAGE;
    }

    public List<Long> getPages() {
        return pages;
    }

    public long getEndPageOfPrevBlock() {
        return endPageOfPrevBlock;
    }

    public long getStartPageOfNextBlock() {
        return startPageOfNextBlock;
    }
}
