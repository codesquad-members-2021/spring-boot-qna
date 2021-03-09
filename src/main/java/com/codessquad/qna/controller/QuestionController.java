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

    @PostMapping("/{id}/answers")
    public String answer(@PathVariable("id") Long id, Answer answer, HttpSession session){
        User writer = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        answer.setQuestion(question);
        answer.setWriter(writer);
        answer.setTime(LocalDateTime.now());
        answerRepository.save(answer);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        answerRepository.findByIdAndQuestionId(answerId, questionId)
                .filter(answer -> answer.matchesWriter(user))
                .ifPresent(answerRepository::delete);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{questionId}/answers/{answerId}/form")
    public ModelAndView updateAnswerForm(@PathVariable("questionId") Long questionId,
                                         @PathVariable("answerId") Long answerId,
                                         HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Answer answer = answerRepository.findByIdAndQuestionIdAndWriter(answerId, questionId, user)
                .orElseThrow(NotFoundException::new);
        return new ModelAndView("qna/update_answer_form", "answer", answer);
    }

    @PutMapping("/{questionId}/answers/{answerId}")
    public String updateAnswer(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               Answer updatedAnswer,
                               HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Answer answer = answerRepository.findByIdAndQuestionId(answerId, questionId)
                .orElseThrow(NotFoundException::new);
        matchesAnswerWriterWithUser(answer, user);
        answer.updateAnswer(updatedAnswer);
        answerRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    private void matchesAnswerWriterWithUser(Answer answer, User user) throws UnauthorizedAccessException{
        if (!answer.matchesWriter(user)) {
            throw new UnauthorizedAccessException();
        }
    }

    private void matchesQuestionWriterWithUser(Question question, User sessionUser) throws UnauthorizedAccessException {
        if (!question.isWriter(sessionUser)) {
            throw new UnauthorizedAccessException();
        }
    }

}
