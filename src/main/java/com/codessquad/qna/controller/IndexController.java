package com.codessquad.qna.controller;

import com.codessquad.qna.domain.pagination.Criteria;
import com.codessquad.qna.domain.pagination.PageDTO;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final QuestionService questionService;

    public IndexController (QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String goMain(Criteria cri, Model model) {
        cri.setPageNum(1);
        cri.setAmount(15);
        model.addAttribute("pageMaker", new PageDTO(cri, 100));
        model.addAttribute("questions", questionService.pagingList(cri));
        return "index";
    }
}
