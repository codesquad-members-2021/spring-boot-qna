package com.codessquad.qna.controller;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequestMapping("/questions/{questionId}/answers")
@Controller
public class AnswerController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "redirect:/users/login";
        }

        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalStateException("해당 질문을 찾을 수 없습니다. id = " + questionId));
        User user = (User) session.getAttribute("sessionedUser");

        answer.setQuestion(question);
        answer.setWriter(user);

        answerRepository.save(answer);

        logger.info(answer.toString());

        return "redirect:/questions/" + questionId;
    }
}
