package com.codessquad.qna.util;

import com.codessquad.qna.dto.PostDto;

public class PostValidation {

    private PostValidation() {}

    public static void validate(PostDto postDto) {
        validateTitle(postDto.getTitle());
        validateAuthor(postDto.getAuthor());
    }

    private static void validateTitle(String title) {
        if(title.isEmpty()){
            throw new IllegalArgumentException("유효하지 않은 제목입니다.");
        }
    }

    private static void validateAuthor(String author) {
        if(author.isEmpty()){
            throw new IllegalArgumentException("유효하지 않은 작가명 입니다.");
        }
    }

}
