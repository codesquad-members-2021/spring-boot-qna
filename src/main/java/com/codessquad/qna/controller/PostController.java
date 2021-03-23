package com.codessquad.qna.controller;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.module.PageFormatter;
import com.codessquad.qna.service.PostService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/form")
    public String getQnaForm() {
        return "qna/form";
    }

    /**
     * 질문 게시글을 게시판에 등록합니다.
     *
     * @param postDto
     * @return
     */
    @PostMapping("")
    public String addPost(@ModelAttribute PostDto postDto, HttpSession httpSession) {
        postDto.setAuthor(HttpSessionUtils.getUserFromSession(httpSession));
        postService.addPost(postDto);
        return "redirect:/";
    }

    /**
     * Repository 에 있는 모든 게시물을 불러옵니다.
     * 현재 페이지 기준으로 계속해서 더해서 옆쪽으로 가도록 페이징 했습니다.
     *
     * @param model
     * @return
     */
    @GetMapping("")
    public String getAllPosts(Model model, @PageableDefault(size = PageFormatter.POSTS_PER_PAGE, sort = "postId") Pageable pageable) {
        PageFormatter pages = new PageFormatter(pageable, postService.findAllPostByPage(pageable), postService.getPosts().size());
        model.addAttribute("posts", postService.findAllPostByPage(pageable));
        model.addAttribute("pages", pages);
        return "index";
    }


    /**
     * 매개변수로 오는 id 값을 기반으로 해당 포스트를 불러옵니다.
     * 만약 해당 유저가 없다면 NotFoundException을 리턴합니다.
     *
     * @param id    Post Id
     * @param model
     * @return
     * @throws
     */
    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "qna/show";
    }

    /**
     * Post UpdateForm 으로 이동할 수 있음.
     * 세션에 로그인 되어 있는 유저와 작성자를 비교하여 틀릴시 IllegalAccessException 을 리턴함
     *
     * @param id    Post id
     * @param model
     * @return
     * @throws IllegalAccessException
     */
    @GetMapping("/{id}/form")
    public String updatePostForm(@PathVariable Long id, HttpSession httpSession, Model model) throws IllegalAccessException {
        Post post = postService.getPost(id);
        User sessionUser = HttpSessionUtils.getUserFromSession(httpSession);
        if (!post.isMatchedAuthor(sessionUser)) {
            throw new IllegalAccessException("다른 사람의 글을 수정할 수 없습니다");
        }
        model.addAttribute("post", post);
        return "qna/updateForm";
    }

    /**
     * postDto 를 받아 기존의 post 를 업데이트 할 수 있도록 하였음
     *
     * @param id      Post id
     * @param postDto
     * @return
     */
    @PutMapping("/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute PostDto postDto) {
        postService.updatePost(id, postDto);
        return "redirect:/";
    }

    /**
     * 해당 id 에 Mapping 되어 있는 게시물을 삭제합니다.
     * 작성자가 아니라면 IllegalAccessException 이 발생합니다.
     *
     * @param id Post id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id, HttpSession httpSession) throws IllegalAccessException {
        Post post = postService.getPost(id);
        User sessionUser = HttpSessionUtils.getUserFromSession(httpSession);
        postService.deletePost(post, sessionUser);
        return "redirect:/";
    }

}
