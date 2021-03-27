package com.codessquad.qna.controller;

import com.codessquad.qna.domain.pagination.Criteria;
import com.codessquad.qna.domain.pagination.PageDTO;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final QuestionService questionService;

    public IndexController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String goMain(Optional<Integer> pageNum, Model model) { // 메인화면(Url 매핑 -> "/") 진입 시 1페이지로 설정
        Criteria criteria = new Criteria(pageNum.orElse(new Integer(1)));
        int notDeletedQuestionSize = questionService.notDeletedSize();
        model.addAttribute("pageMaker", new PageDTO(criteria, notDeletedQuestionSize));
        model.addAttribute("questions", questionService.pagingList(criteria));
        return "index";
    }
}
