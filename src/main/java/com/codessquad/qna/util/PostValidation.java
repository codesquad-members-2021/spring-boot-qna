package com.codessquad.qna.util;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.User;

public class PostValidation {

    private PostValidation() {
    }

    public static void validate(PostDto postDto) {
        validateTitle(postDto.getTitle());
        validateAuthor(postDto.getAuthor());
    }

    private static void validateTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 제목입니다.");
        }
    }

    private static void validateAuthor(User author) {
        if (author == null) {
            throw new IllegalArgumentException("유효하지 않은 유저 입니다.");
        }
    }

}
