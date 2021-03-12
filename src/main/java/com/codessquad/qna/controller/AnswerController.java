package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@Controller
public class AnswerController {

    private final Logger logger = LoggerFactory.getLogger(AnswerController.class);
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answer/{questionId}")
    public String createAnswer(@PathVariable("questionId") Long questionId, Answer answer, HttpSession session) {
        this.answerService.save(questionId, answer, getUserFromSession(session));
        logger.info("댓글 등록 요청");
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/answer/{id}")
    public String viewUpdateAnswer(@PathVariable("id") Long id, Model model, HttpSession session) {
        Answer answer = this.answerService.verifyAnswer(id, getUserFromSession(session));
        Long questionId = this.answerService.findQuestionId(id);
        model.addAttribute("answer", answer);
        logger.info("댓글 수정 페이지 요청");
        return answer.nonNull() ? "qna/updateAnswer" : "redirect:/question/" + questionId;
    }

    @PutMapping("/answer/{id}")
    public String updateAnswer(@PathVariable("id") Long id, Answer answer, HttpSession session) {
        boolean result = this.answerService.update(id, answer, getUserFromSession(session));
        Long questionId = this.answerService.findQuestionId(id);
        logger.info("댓글 수정 요청");
        return result ? "redirect:/question/" + questionId : "redirect:/answer/" + id;
    }

    @DeleteMapping("/answer/{id}")
    public String deleteAnswer(@PathVariable("id") Long id, HttpSession session) {
        Long questionId = this.answerService.findQuestionId(id);
        this.answerService.delete(id, getUserFromSession(session));
        return (questionId != -1) ? "redirect:/question/" + questionId : "redirect:/";
    }

}
