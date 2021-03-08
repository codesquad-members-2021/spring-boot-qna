package com.codessquad.qna.util;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;

public class Mapper {

    private Mapper(){}

    public static User mapToUser(UserDto userDto) {
        UserValidation.validate(userDto);
        return new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
    }

    public static Post mapToPost(PostDto postDto) {
        PostValidation.validate(postDto);
        return new Post(postDto.getTitle(), postDto.getAuthor(), postDto.getBody());
    }

}
