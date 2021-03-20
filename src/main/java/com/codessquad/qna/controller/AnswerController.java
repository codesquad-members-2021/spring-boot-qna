package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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
        HttpSessionUtils.checkValidOf(session);
        answer = Optional.ofNullable(answer).orElseThrow(IllegalArgumentException::new);
        answerService.save(HttpSessionUtils.getLoginUserOf(session), answer);
        return "redirect:/questions/{question.id}";
    }

    @DeleteMapping("/questions/{question.id}/answers/{answerId}")
    public String deleteAnswer(HttpSession session, @PathVariable("answerId") Long id) {
        ValidUtils.checkIllegalArgumentOf(id);
        HttpSessionUtils.checkValidOf(session);

        User loginUser = HttpSessionUtils.getLoginUserOf(session);
        Answer selectedAnswer = answerService.findById(id);

        answerService.delete(loginUser, selectedAnswer);

        return "redirect:/questions/{question.id}";
    }

    @GetMapping("/questions/{question.id}/answers/{answerId}/update")
    public String modifyAnswerButton(@PathVariable("question.id") Long targetId, @PathVariable Long answerId, Model model) {
        ValidUtils.checkIllegalArgumentOf(targetId, answerId);

        Question questionData = questionService.findById(targetId);
        Answer selectedAnswer = answerService.getSelectedAnswers(questionData, answerId);

        model.addAttribute("answer", selectedAnswer); // 수정할 댓글만 가져온다.
        model.addAttribute("question", questionData);
        return "qna/updateShow";
    }

    @PutMapping("/questions/{question.id}/answers/{answerId}")
    public String updateAnswer(HttpSession session, @PathVariable("answerId") Long id, String replyContents) {
        ValidUtils.checkIllegalArgumentOf(id);
        HttpSessionUtils.checkValidOf(session);

        User loginUser = HttpSessionUtils.getLoginUserOf(session);
        Answer selectedAnswer = answerService.findById(id);

        answerService.update(loginUser,selectedAnswer,replyContents);
        return "redirect:/questions/{question.id}";
    }

}
