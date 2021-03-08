package com.codessquad.qna.controller;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.exception.NotExistLoggedUserInSession;
import com.codessquad.qna.service.PostService;
import com.codessquad.qna.util.HttpSessionUtils;
import com.codessquad.qna.util.Mapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        model.addAttribute("post", postService.getPost(id));
        return "qna/show";
    }

    /**
     * Post UpdateForm 으로 이동할 수 있음.
     * 세션에 로그인 되어 있는 유저와 작성자를 비교하여 틀릴시 IllegalAccessException 을 리턴함
     * @param id
     * @param model
     * @throws IllegalAccessException
     * @return
     */
    @GetMapping("/questions/{id}/form")
    public String updatePostForm(@PathVariable Long id, HttpSession httpSession, Model model) throws IllegalAccessException {
        Post post = postService.getPost(id);
        User userFromSession = HttpSessionUtils.getUserFromSession(httpSession);
        if(!post.isMatchedAuthor(userFromSession)) {
            throw new IllegalAccessException("다른 사람의 글을 수정할 수 없습니다");
        }
        model.addAttribute("post", post);
        return "qna/updateForm";
    }

    /**
     * postDto 를 받아 기존의 post 를 업데이트 할 수 있도록 하였음
     * @param id
     * @param postDto
     * @return
     */
    @PutMapping("/questions/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute PostDto postDto) {
        Post post = postService.getPost(id);
        postService.updatePost(post, Mapper.mapToPost(postDto));
        return "redirect:/";
    }
}
