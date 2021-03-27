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
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        this.answerService.save(questionId, answer, getUserFromSession(session));
        logger.info("댓글 등록 요청");
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/answer/{id}")
    public String viewUpdateAnswer(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("answer", this.answerService.verifyAnswer(id, getUserFromSession(session)));
        logger.info("댓글 수정 페이지 요청");
        return "qna/updateAnswer";
    }

    @PutMapping("/answer/{id}")
    public String updateAnswer(@PathVariable Long id, Answer answer, HttpSession session) {
        this.answerService.update(id, answer, getUserFromSession(session));
        logger.info("댓글 수정 요청");
        return "redirect:/question/" + this.answerService.findQuestionId(id);
    }

    @DeleteMapping("/answer/{id}")
    public String deleteAnswer(@PathVariable Long id, HttpSession session) {
        this.answerService.delete(id, getUserFromSession(session));
        logger.info("댓글 삭제 요청");
        return "redirect:/question/" + this.answerService.findQuestionId(id);
    }

}
