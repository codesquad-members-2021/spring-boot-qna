package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    public static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/form")
    public String showQuestionForm(HttpSession session) {
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping
    public String createQuestion(HttpSession session, String title, String contents) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = new Question(sessionedUser, title, contents);
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping
    public String readQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findQuestions());
        return "index";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다."));
        if(! question.isSameWriter(sessionedUser)) {
           return "redirect:/users/loginForm";
        }
        model.addAttribute("question", question);

        return "/qna/update";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, HttpSession session, Question newQuestion) {
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다."));
        if(! question.isSameWriter(sessionedUser)) {
            return "redirect:/users/loginForm";
        }
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/questions";

    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        List<Answer> answers = answerRepository.findByQuestionId(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다."));

        if(! question.isSameWriter(sessionedUser)) {
            throw new IllegalArgumentException("질문자와 로그인한 사용자가 다른 경우 삭제할 수 없습니다.");
        }

        for(int i = 0 ; i < answers.size() ; i++) {
            if(! answers.get(i).getWriter().equals(sessionedUser) ) {
                throw new IllegalArgumentException("로그인한 사용자가 아닌 답변자가 존재하는 경우 삭제할 수 없습니다.");
            }
            if(! answers.get(i).getWriter().equals(question.getWriter())) {
                throw new IllegalArgumentException("질문자와 답변자가 다른 경우 삭제할 수 없습니다.");
            }
        }

        question.delete();
        questionRepository.save(question);
        return "redirect:/questions";
    }

}
