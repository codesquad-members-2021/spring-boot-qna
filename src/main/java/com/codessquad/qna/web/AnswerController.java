package com.codessquad.qna.web;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.AnswerNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.domain.user.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private QuestionService questionService;
    private AnswerService answerService;

    public AnswerController(QuestionService questionService,
                            AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/")
    public String createAnswer(@PathVariable Long questionId, String comment,
                               HttpSession session) {
        User writer = getUserFromSession(session);
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(writer, question, comment);

        answerService.createAnswer(answer);
        logger.debug("answer : {}", answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId,
                        @PathVariable Long id,
                         HttpSession session) {
        User loggedInUser = getUserFromSession(session);
        Answer answer = answerService.findByIdIfExist(id);

        if(!answer.matchWriter(loggedInUser)) {
            throw new IllegalUserAccessException();
        }

        answerService.delete(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public String handleAnswerNotFoundException() {
        return "redirect:/";
    }
}
