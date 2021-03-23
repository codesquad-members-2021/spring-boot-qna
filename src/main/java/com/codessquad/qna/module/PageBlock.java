package com.codessquad.qna.module;

import java.util.ArrayList;
import java.util.List;

public class PageBlock {

    private static final int DEFAULT_PAGE_COUNT = 4;

    private final List<Integer> blocks;
    private final int blockSize;

    public PageBlock(int blockSize) {
        this.blockSize = blockSize;
        this.blocks = new ArrayList<>(blockSize);
    }

    public void createPageBlocks(int currentPage) {
        int maxPageIdx = currentPage + DEFAULT_PAGE_COUNT;

        if (blockSize < maxPageIdx) {
            maxPageIdx = blockSize;
        }

        int count = maxPageIdx - (currentPage + 1);

        if (count <= 4) {
            currentPage -= (DEFAULT_PAGE_COUNT - (count + 1));
        }

        for (int i = currentPage; i <= maxPageIdx; i++) {
            if (i > blockSize) {
                break;
            }
            blocks.add(i);
        }
    }

    public List<Integer> getBlocks() {
        return blocks;
    }

    private int getStartIndex() {
        return blockSize / 5;
    }

}
