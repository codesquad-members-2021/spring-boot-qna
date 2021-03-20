package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.*;
import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class AnswersController {
    private final QuestionRepository questionRepository;
    private final AnswersRepository answersRepository;

    public AnswersController(AnswersRepository answersRepository, QuestionRepository questionRepository) {
        this.answersRepository = answersRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question targetQuestion = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        answersRepository.save(new Answer(answerContents, targetQuestion, sessionUser));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/answers/{answerId}")
    public String deleteAnswer(@PathVariable("answerId") long answerId, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Answer targetAnswer = answersRepository.findByIdAndDeletedFalse(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        if (!targetAnswer.isMatchingWriter(sessionUser)) {
            throw new UnauthorizedAccessException("자신의 답변만 삭제할 수 있습니다");
        }
        this.answersRepository.delete(targetAnswer);

        Question targetQuestion = targetAnswer.getQuestion();
        return "redirect:/questions/" + targetQuestion.getId();
    }

}
