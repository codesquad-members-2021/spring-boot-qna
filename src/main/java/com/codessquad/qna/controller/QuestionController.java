package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

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
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping()
    public String query(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(sessionUser);
        question.setTime(LocalDateTime.now());
        question.setPoint(0);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView qnaShow(@PathVariable("id") Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (!questionOptional.isPresent()) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView("qna/show");
        Question question = questionOptional.get();
        modelAndView.addObject("question", question);
        modelAndView.addObject("comments", answerRepository.findAllByQuestion(question));
        modelAndView.addObject("commentsSize", answerRepository.countByQuestion(question));
        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateForm(@PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return new ModelAndView("redirect:/questions/unauthorized");
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문"));
        if (!question.isWriter(sessionUser)) {
            return new ModelAndView("redirect:/questions/unauthorized");
        }
        ModelAndView modelAndView = new ModelAndView("/qna/update_form");
        modelAndView.addObject("question", question);
        return modelAndView;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, Question updatedQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/questions/unauthorized";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문"));
        if (!question.isWriter(sessionUser)) {
            return "redirect:/questions/unauthorized";
        }
        question.updateContents(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/questions/unauthorized";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문"));
        if (!question.isWriter(sessionUser)) {
            return "redirect:/questions/unauthorized";
        }
        questionRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "qna/unauthorized";
    }

    @PostMapping("/{id}/answers")
    public String answer(@PathVariable("id") Long id, Answer answer, HttpSession session){
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/questions/" + id;
        }
        User writer = HttpSessionUtils.getUserFromSession(session);
        questionRepository.findById(id)
                .ifPresent(question -> {
                    answer.setQuestion(question);
                    answer.setWriter(writer);
                    answer.setTime(LocalDateTime.now());
                    answerRepository.save(answer);
                });
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            //error
            return "redirect:/users/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        answerRepository.findByIdAndQuestionIdAndWriter(answerId, questionId, user)
                .ifPresent(answerRepository::delete);

        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{questionId}/answers/{answerId}/form")
    public ModelAndView updateAnswerForm(@PathVariable("questionId") Long questionId,
                                         @PathVariable("answerId") Long answerId,
                                         HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            //error
            return new ModelAndView("redirect:/users/login");
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findByIdAndQuestionIdAndWriter(answerId, questionId, user)
                .orElseThrow(() -> new IllegalStateException("잘못된 접근"));

        return new ModelAndView("qna/update_answer_form", "answer", answer);
    }

    @PutMapping("/{questionId}/answers/{answerId}")
    public String updateAnswer(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               Answer updatedAnswer,
                               HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            //error
            return "redirect:/users/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        answerRepository.findByIdAndQuestionIdAndWriter(answerId, questionId, user)
                .ifPresent(answer -> {
                    answer.updateAnswer(updatedAnswer);
                    answerRepository.save(answer);
                });

        return "redirect:/questions/" + questionId;
    }

}
