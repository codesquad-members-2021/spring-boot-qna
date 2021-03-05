package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class QuestionController {
    private List<Question> questionList = new ArrayList<>();

    @PostMapping("/questions")
    private String questions(Question question, Model model) {
        question.setId(questionList.size() + 1); // 질문 객체 아이디 정의
        question.setTime(LocalDateTime.now()); // 게시글 시간 설정
        questionList.add(question);
        model.addAttribute("questions", questionList);
        return "redirect:/";
    }

    @GetMapping("/")
    private String questionsList(Model model) {
        model.addAttribute("questions", questionList);
        return "/index";
    }

    @GetMapping("/questions/{id}")
    private String showQuestionContents(@PathVariable("id") int targetId, Model model) {
        for (Question findQuestion : questionList) {
            if (Objects.equals(findQuestion.getId(), targetId)) {
                model.addAttribute("invalidMember", false);
                model.addAttribute("title", findQuestion.getTitle());
                model.addAttribute("writer", findQuestion.getWriter());
                model.addAttribute("time", findQuestion.getTime());
                model.addAttribute("contents", findQuestion.getContents());
                break;
            } else {
                model.addAttribute("invalidMember", true);
            }
        }
        return "qna/show";
    }

}
