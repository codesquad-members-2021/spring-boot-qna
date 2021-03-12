package com.codessquad.qna.web.answers;

import com.codessquad.qna.web.exceptions.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.QuestionNotFoundException;
import com.codessquad.qna.web.questions.Question;
import com.codessquad.qna.web.questions.QuestionRepository;
import com.codessquad.qna.web.users.User;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswersController {

    private final QuestionRepository questionRepository;

    private final AnswersRepository answersRepository;

    public AnswersController(QuestionRepository questionRepository,
                             AnswersRepository answersRepository) {
        this.questionRepository = questionRepository;
        this.answersRepository = answersRepository;
    }

    @PostMapping("/questions/{questionId}/answers")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question targetQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        if (targetQuestion == null) {
            return "redirect:/";
        }
        Answer answer = new Answer(answerContents);
        answer.setQuestion(targetQuestion);
        answer.setWriter(sessionUser);
        answersRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/answers/{answerId}")
    public String deleteAnswer(@PathVariable("answerId") long answerId, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Answer targetAnswer = answersRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);
        if (targetAnswer == null) {
            return "redirect:/";
        }
        Question targetQuestion = targetAnswer.getQuestion();
        if (!targetAnswer.isMatchingWriter(sessionUser)) {
            return "redirect:/questions/" + targetQuestion.getId();
        }
        this.answersRepository.delete(targetAnswer);
        return "redirect:/questions/" + targetQuestion.getId();
    }

}
