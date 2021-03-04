package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    List<Qna> qnaList = new ArrayList<>();

    @PostMapping("/qna")
    public String qnaMain(String writer, String title, String contents) {
        Qna newQna = new Qna(qnaList.size() + 1, writer, title, contents);
        System.out.println("QnA: " + writer);
        qnaList.add(newQna);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQnaList(Model model) {
        model.addAttribute("qnaList", qnaList);
        return "index";
    }

    @GetMapping("qna/show/{qnaId}")
    public String getOneQuestion(@PathVariable("qnaId") int qnaId, Model model) {
        Qna foundQuestion = qnaList.stream()
                .filter((qua -> qua.getId() == qnaId))
                .findFirst().orElse(null);
        model.addAttribute("Qna", foundQuestion);
        return "qna/show";
    }
}
