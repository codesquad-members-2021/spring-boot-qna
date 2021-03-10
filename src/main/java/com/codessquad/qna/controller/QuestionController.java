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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/questions")
@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public ModelAndView getQuestionForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("/qna/form");

        if (!HttpSessionUtils.isLoginUser(session)) {
            mav.setViewName("redirect:/users/login");
            return mav;
        }

        return mav;
    }

    @PostMapping("/")
    public String createQuestion(Question question, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        question.setWriter(sessionedUser);
        logger.info(question.toString());
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));

        List<Answer> answers = answerRepository.findAllByQuestionId(id);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUpdateForm(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("/qna/update_form");

        Question question = (Question) questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));

        if (!HttpSessionUtils.isLoginUser(session)) {
            mav.setViewName("redirect:/users/login");
            return mav;
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 글만 수정할 수 있습니다.");
        }

        mav.addObject("question", question);
        return mav;
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question questionWithUpdatedInfo) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 글을 찾을 수 없습니다. id = " + id));

        question.update(questionWithUpdatedInfo);
        questionRepository.save(question);

        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 글을 찾을 수 없습니다. id = " + id));

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 글만 삭제할 수 있습니다.");
        }

        questionRepository.delete(question);
        return "redirect:/";
    }
}
