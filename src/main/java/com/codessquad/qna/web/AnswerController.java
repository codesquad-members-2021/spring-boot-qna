package com.codessquad.qna.web;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.AnswerNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.domain.user.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerController(QuestionRepository questionRepository,
                            AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/")
    public String createAnswer(@PathVariable Long questionId, Answer answer,
                               HttpSession session) {
        User writer = getUserFromSession(session);
        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        answer.setQuestion(question);
        answer.setWriter(writer);
        answerRepository.save(answer);

        logger.debug("answer : {}", answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId,
                         @PathVariable Long id,
                         HttpSession session) {
        User loginedUser = getUserFromSession(session);
        Answer answer = answerRepository.findById(id)
                .orElseThrow(AnswerNotFoundException::new);

        if(!answer.matchWriter(loginedUser)) {
            throw new IllegalUserAccessException();
        }

        answerRepository.delete(answer);
        questionRepository.findById(questionId);

        return "redirect:/questions/" + questionId;
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public String handleAnswerNotFoundException() {
        return "redirect:/";
    }
}
