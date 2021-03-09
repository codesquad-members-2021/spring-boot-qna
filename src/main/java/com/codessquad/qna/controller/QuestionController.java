package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            throw new NotLoggedInException();
        }
        return "qna/form";
    }

    @PostMapping()
    public String query(Question question, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        question.setWriter(sessionUser);
        question.setTime(LocalDateTime.now());
        question.setPoint(0);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView qnaShow(@PathVariable("id") Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("qna/show");
        modelAndView.addObject("question", question);
        modelAndView.addObject("comments", answerRepository.findAllByQuestion(question));
        modelAndView.addObject("commentsSize", answerRepository.countByQuestion(question));
        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateForm(@PathVariable("id") Long id, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        matchesQuestionWriterWithUser(question, sessionUser);
        ModelAndView modelAndView = new ModelAndView("/qna/update_form");
        modelAndView.addObject("question", question);
        return modelAndView;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, Question updatedQuestion, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        matchesQuestionWriterWithUser(question, sessionUser);
        question.updateContents(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        questionRepository.findById(id)
                .ifPresent(question -> {
                    matchesQuestionWriterWithUser(question, sessionUser);
                    questionRepository.deleteById(id);
                });
        return "redirect:/";
    }

    private void matchesQuestionWriterWithUser(Question question, User sessionUser) throws UnauthorizedAccessException {
        if (!question.isWriter(sessionUser)) {
            throw new UnauthorizedAccessException();
        }
    }

}
