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
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping()
    public String answer(@PathVariable("questionId") Long id, Answer answer, HttpSession session){
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

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Answer answer = answerRepository.findByIdAndQuestionId(answerId, questionId)
                .orElseThrow(NotFoundException::new);
        matchesAnswerWriterWithUser(answer, user);
        answerRepository.delete(answer);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/form")
    public ModelAndView updateAnswerForm(@PathVariable("questionId") Long questionId,
                                         @PathVariable("answerId") Long answerId,
                                         HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session)
                .orElseThrow(NotLoggedInException::new);
        Answer answer = answerRepository.findByIdAndQuestionId(answerId, questionId)
                .orElseThrow(NotFoundException::new);
        matchesAnswerWriterWithUser(answer, user);
        return new ModelAndView("qna/update_answer_form", "answer", answer);
    }

    @PutMapping("/{answerId}")
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

    private void matchesAnswerWriterWithUser(Answer answer, User user) throws UnauthorizedAccessException {
        if (!answer.matchesWriter(user)) {
            throw new UnauthorizedAccessException();
        }
    }
}
