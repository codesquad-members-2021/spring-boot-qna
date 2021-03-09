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
        if (!HttpSessionUtils.isLoggedIn(session)) {
            throw new NotLoggedInException();
        }
        return "qna/form";
    }

    @PostMapping()
    public String query(Question question, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(sessionUser);
        question.setTime(LocalDateTime.now());
        question.setPoint(0);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public ModelAndView qnaShow(@PathVariable("questionId") Long id) {
        Question question = questionRepository.findById(id).orElseThrow(NotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("qna/show");
        modelAndView.addObject("question", question);
        modelAndView.addObject("comments", answerRepository.findAllByQuestion(question));
        modelAndView.addObject("commentsSize", answerRepository.countByQuestion(question));
        return modelAndView;
    }

    @GetMapping("/{questionId}/form")
    public ModelAndView updateForm(@PathVariable("questionId") Long id, HttpSession session) {
        return new ModelAndView("/qna/update_form",
                "question", getQuestionWithCheckSession(id, session));
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable("questionId") Long id, Question updatedQuestion, HttpSession session) {
        Question question = getQuestionWithCheckSession(id, session);
        question.updateContents(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        questionRepository.findById(id)
                .ifPresent(question -> {
                    matchesQuestionWriterWithUser(question, sessionUser);
                    questionRepository.deleteById(id);
                });
        return "redirect:/";
    }

    private Question getQuestionWithCheckSession(Long id, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(NotFoundException::new);
        matchesQuestionWriterWithUser(question, sessionUser);
        return question;
    }

    private void matchesQuestionWriterWithUser(Question question, User sessionUser) {
        if (!question.isWriter(sessionUser)) {
            throw new UnauthorizedAccessException("다른 사람의 질문을 수정하거나 삭제할 수 없습니다.");
        }
    }

}
