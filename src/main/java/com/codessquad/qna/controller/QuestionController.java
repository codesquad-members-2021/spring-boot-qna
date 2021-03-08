package com.codessquad.qna.controller;

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

@RequestMapping("/questions")
@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public ModelAndView getQuestionForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("/qna/form");
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            mav.setViewName("redirect:/users/login"); // 로그인 과정에서 session에 sessionUser가 할당되므로 이후 추가 할당 작업 필요하지 않다.
            return mav;
        }

        return mav;
    }

    @PostMapping("/")
    public String createQuestion(Question question) {
        logger.info(question.toString());
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUpdateForm(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("/qna/update_form");
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        Question question = (Question) questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));

        if (sessionedUser == null) {
            mav.setViewName("redirect:/users/login");
            return mav;
        }

        if (!sessionedUser.isSameUserId(question.getWriter())) {
            throw new IllegalStateException("자신이 작성한 글만 수정할 수 있습니다.");
        }

        mav.addObject("question", question);
        return mav;
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question questionWithUpdatedInfo) {
        Question targetQuestion = questionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 글을 찾을 수 없습니다. id = " + id));

        targetQuestion.update(questionWithUpdatedInfo);
        questionRepository.save(targetQuestion);

        return "redirect:/questions/" + id;
    }
}
