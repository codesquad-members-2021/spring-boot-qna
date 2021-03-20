package com.codessquad.qna.module;

import java.util.ArrayList;
import java.util.List;

public class PageBlock {

    private static final int DEFAULT_PAGE_COUNT = 5;

    private final List<Integer> blocks;
    private final int blockSize;

    public PageBlock(int blockSize) {
        this.blockSize = blockSize;
        this.blocks = new ArrayList<>(blockSize);
    }

    public void createPageBlocks(int currentPage) {
        int pageCount = blockSize - currentPage + 1;
        currentPage = validateStartPage(currentPage, pageCount);
        int maxPage = currentPage + DEFAULT_PAGE_COUNT;
        for (int i = currentPage; i < maxPage; i++) {
            if (i > blockSize) {
                break;
            }
            blocks.add(i);
        }
    }

    public List<Integer> getBlocks() {
        return blocks;
    }

    private int validateStartPage(int currentPage, int pageCount) {
        if (currentPage > 5) {
            currentPage -= (DEFAULT_PAGE_COUNT - pageCount);
        } else if (pageCount < 5) {
            currentPage /= 5;
        }
        return currentPage;
    }


    private int getStartIndex() {
        return blockSize / 5;
    }

}
