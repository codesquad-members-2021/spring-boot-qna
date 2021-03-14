package com.codessquad.qna.web.answers;

import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.questions.Question;
import com.codessquad.qna.web.users.User;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class AnswersController {
    private final AnswersRepository answersRepository;

    public AnswersController(AnswersRepository answersRepository) {
        this.answersRepository = answersRepository;
    }

    @DeleteMapping("/answers/{answerId}")
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
