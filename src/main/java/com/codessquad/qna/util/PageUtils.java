package com.codessquad.qna.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageUtils {
    private PageUtils() {

    }

    public static List<Integer> getPageNumbers(int pageNum, int totalPageNum, int pageListSize) {
        int startPageNum = (pageNum - 1) / pageListSize * pageListSize + 1;

        int lastPageNumCalculated = startPageNum + pageListSize - 1;
        int lastPageNum = lastPageNumCalculated < totalPageNum ? lastPageNumCalculated : totalPageNum;

        return IntStream.range(startPageNum, lastPageNum + 1).boxed().collect(Collectors.toList());
    }

    public static int getPrevPageNumber(List<Integer> pageNumbers) {
        if (pageNumbers != null && !pageNumbers.isEmpty()) {
            int firstPageNum = pageNumbers.get(0);
            if (firstPageNum <= 1) {
                return 1;
            }
            return firstPageNum - 1;
        }
        return 1;
    }

    public static int getNextPageNumber(List<Integer> pageNumbers, int totalPageNum) {
        if (pageNumbers != null && !pageNumbers.isEmpty()) {
            int lastPageNum = pageNumbers.get(pageNumbers.size() - 1);
            if (lastPageNum < totalPageNum) {
                return lastPageNum + 1;
            }
            return lastPageNum;
        }
        return 1;
    }
}
