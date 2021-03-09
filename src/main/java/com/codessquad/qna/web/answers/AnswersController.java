package com.codessquad.qna.web.answers;

import com.codessquad.qna.web.questions.Question;
import com.codessquad.qna.web.questions.QuestionRepository;
import com.codessquad.qna.web.users.User;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswersController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswersRepository answersRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session, Model model) {
        User sessionUser = SessionUtil.getSessionUser(session);
        if (sessionUser == null) {
            return "redirect:/";
        }
        Question targetQuestion = questionRepository.findById(questionId).orElse(null);
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
        User sessionUser = SessionUtil.getSessionUser(session);
        if (sessionUser == null) {
            return "redirect:/";
        }
        Answer targetAnswer = answersRepository.findById(answerId).orElse(null);
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
