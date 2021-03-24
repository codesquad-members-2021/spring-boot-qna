package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by 68936@naver.com on 2021-03-16 오전 2:28
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping("/questions/{question.id}/answers")
    public String createAnswer(Answer answer, HttpSession session) {
        answerService.save(session, answer);
        return "redirect:/questions/{question.id}";
    }

    @DeleteMapping("/questions/{question.id}/answers/{answerId}")
    public String deleteAnswer(HttpSession session, @PathVariable Long answerId) {
        answerService.delete(session, answerId);
        return "redirect:/questions/{question.id}";
    }

    @GetMapping("/questions/{question.id}/answers/{answerId}/update")
    public String modifyAnswerButton(@PathVariable("question.id") Long targetId, @PathVariable Long answerId, Model model) {
        Question questionData = questionService.findById(targetId);
        Answer selectedAnswer = answerService.getSelectedAnswers(questionData, answerId);

        model.addAttribute("answer", selectedAnswer); // 수정할 댓글만 가져온다.
        model.addAttribute("question", questionData);
        return "qna/updateShow";
    }

    @PutMapping("/questions/{question.id}/answers/{answerId}")
    public String updateAnswer(HttpSession session, @PathVariable("answerId") Long id, String replyContents) {
        answerService.update(session, id, replyContents);
        return "redirect:/questions/{question.id}";
    }

}
