package com.codessquad.qna.util;

import com.codessquad.qna.dto.PostDto;

public class PostValidation {

    private PostValidation() {
    }

    public static void validate(PostDto postDto) {
        validateTitle(postDto.getTitle());
    }

    private static void validateTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 제목입니다.");
        }
    }

}
