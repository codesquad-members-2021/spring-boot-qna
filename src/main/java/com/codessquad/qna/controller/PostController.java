package com.codessquad.qna.controller;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/questions")
    public String addPost(@RequestParam String writer, @RequestParam String title, @RequestParam String contents) {
        Post post = new Post(writer, title, contents);
        postService.addPost(post);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String getPost(@PathVariable int id, Model model) {
        model.addAttribute("post", postService.getPost(id-1));
        return "qna/show";
    }

}
