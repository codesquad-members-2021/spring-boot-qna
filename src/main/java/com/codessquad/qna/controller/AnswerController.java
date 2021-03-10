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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalStateException("해당 질문을 찾을 수 없습니다. id = " + questionId));
        int answerCount = answerRepository.countAnswersByQuestionId(questionId);
        question.setAnswerCount(++answerCount);

        answer.setQuestion(question);
        answer.setWriter(sessionedUser);

        answerRepository.save(answer);

        logger.info(answer.toString());

        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUpdateForm(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("/answers/update_form");
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalStateException("해당 질문이 없습니다. id = " + questionId));
        Answer answer = answerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 답변이 없습니다. id = " + id));

        if (!HttpSessionUtils.isLoginUser(session)) {
            mav.setViewName("redirect:/users/login");
            return mav;
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 답변만 수정할 수 있습니다.");
        }

        mav.addObject("question", question);
        mav.addObject("answer", answer);

        return mav;
    }

    @PutMapping("/{id}")
    public ModelAndView updateAnswer(@PathVariable Long questionId, @PathVariable Long id, Answer answerWithUpdateInfo) {
        ModelAndView mav = new ModelAndView("redirect:/questions/" + questionId);
        Answer answer = answerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 답변이 없습니다. id = " + id));

        answer.update(answerWithUpdateInfo);
        answerRepository.save(answer);

        return mav;
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Answer answer = answerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 답변이 없습니다. id = " + id));

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 답변만 삭제할 수 있습니다.");
        }

        answerRepository.delete(answer);

        return "redirect:/questions/" + questionId;
    }
}
