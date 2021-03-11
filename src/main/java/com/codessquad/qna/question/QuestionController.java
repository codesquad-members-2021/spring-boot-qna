package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public ModelAndView getQuestions() {
        return new ModelAndView("/qna/list", "questions", questionRepository.findAll());
    }

    @PostMapping
    public String createQuestions(Question question, HttpSession session) {
        if (session.getAttribute("sessionedUser") == null) {
            return "redirect:/users/login/form";
        }

        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView getQuestion(@PathVariable Long id) {
        Question question = questionRepository.findById(id).get();
        return new ModelAndView("/qna/show", "question", question);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        long questionWriterId = question.getId().longValue();

        User sessionUser = ((User) session.getAttribute("sessionedUser"));
        if (sessionUser == null) {
            return "redirect:/users/login/form";
        }
        long sessionUserId = sessionUser.getId().longValue();

        if (questionWriterId != sessionUserId) {
            throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
        }

        questionRepository.delete(question);

        return "redirect:/questions";
    }
}
