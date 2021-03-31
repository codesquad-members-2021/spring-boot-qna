package com.codessquad.qna.controller;

import com.codessquad.qna.domain.pagination.Criteria;
import com.codessquad.qna.domain.pagination.PageDTO;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.data.domain.Page;
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
        Criteria criteria = new Criteria(pageNum.orElse(1));
        Page<Question> questions = questionService.pagingList(criteria);
        model.addAttribute("pageMaker", new PageDTO(questions));
        model.addAttribute("questions", questions);
        return "index";
    }
}
