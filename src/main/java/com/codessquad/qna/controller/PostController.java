package com.codessquad.qna.controller;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.service.PostService;
import com.codessquad.qna.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PostController {

    private Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;

    /**
     * 질문 게시글을 게시판에 등록합니다.
     * @param postDto
     * @return
     */
    @PostMapping("/questions")
    public String addPost(@ModelAttribute PostDto postDto) {
        Post post = Mapper.mapToPost(postDto);
        postService.addPost(post);
        return "redirect:/";
    }

    /**
     * Repository 에 있는 모든 게시물을 불러옵니다.
     * @param model
     * @return
     */
    @GetMapping("/")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "index";
    }

    /**
     * 매개변수로 오는 id 값을 기반으로 해당 포스트를 불러옵니다.
     * 만약 해당 유저가 없다면 CanNotFindPostException를 리턴합니다.
     * @param id
     * @param model
     * @throws CanNotFindPostException
     * @return
     */
    @GetMapping("/questions/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        try{
            Post post = postService.getPost(id);
            model.addAttribute("post", post);
        }catch (CanNotFindPostException e){
            logger.error(e.getMessage());
        }
        return "qna/show";
    }

    /**
     * IllegalArgumentException 을 핸들링 해주는 메소드
     * 지금은 그냥 redirect 를 하지만 나중에는 다른 페이지로 넘겨주는 작업이 필요할듯함.
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(Exception e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }

}
