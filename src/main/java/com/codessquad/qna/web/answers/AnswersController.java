package com.codessquad.qna.web.answers;

import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.questions.Question;
import com.codessquad.qna.web.questions.QuestionRepository;
import com.codessquad.qna.web.users.User;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/answers")
public class AnswersController {
    private final QuestionRepository questionRepository;
    private final AnswersRepository answersRepository;

    public AnswersController(AnswersRepository answersRepository, QuestionRepository questionRepository) {
        this.answersRepository = answersRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping("/{questionId}")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question targetQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        answersRepository.save(new Answer(answerContents, targetQuestion, sessionUser));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable("answerId") long answerId, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Answer targetAnswer = answersRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        Question targetQuestion = targetAnswer.getQuestion();
        if (!targetAnswer.isMatchingWriter(sessionUser)) {
            throw new UnauthorizedAccessException();
        }

        this.answersRepository.delete(targetAnswer);
        return "redirect:/questions/" + targetQuestion.getId();
    }

}
