package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.exception.AnswerNotFoundException;
import com.codessquad.qna.web.exception.CRUDAuthenticationException;
import com.codessquad.qna.web.exception.QuestionNotFoundException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping()
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);

        Question question = getQuestionById(questionId);
        Answer answer = Answer.toEntity(loginUser, question, contents);
        answerRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Cannot found answer number " + id));

        if (!answer.isMatchingWriter(loginUser)) {
            throw new CRUDAuthenticationException("Only writer can delete this answer post!");
        }
        answerRepository.delete(answer);
        return "redirect:/questions/" + questionId;
    }

    private Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Cannot found question number " + id));
    }


}
