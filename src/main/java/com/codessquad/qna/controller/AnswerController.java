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
    public String creatAnswer(@PathVariable("questionId") Long id, Answer answer, HttpSession session){
        User writer = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        answer.setQuestion(question);
        answer.setWriter(writer);
        answer.setTime(LocalDateTime.now());
        answerRepository.save(answer);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               HttpSession session) {
        answerRepository.delete(getAnswerWithCheckSession(questionId, answerId, session));
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/form")
    public ModelAndView updateForm(@PathVariable("questionId") Long questionId,
                                         @PathVariable("answerId") Long answerId,
                                         HttpSession session) {
        return new ModelAndView("qna/updateAnswerForm",
                "answer", getAnswerWithCheckSession(questionId, answerId, session));
    }

    @PutMapping("/{answerId}")
    public String update(@PathVariable("questionId") Long questionId,
                               @PathVariable("answerId") Long answerId,
                               Answer updatedAnswer,
                               HttpSession session) {
        Answer answer = getAnswerWithCheckSession(questionId, answerId, session);
        answer.updateAnswer(updatedAnswer);
        answerRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    private Answer getAnswerWithCheckSession (Long questionId, Long answerId, HttpSession session)
            throws NotLoggedInException, NotFoundException {
        User user = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findByIdAndQuestionId(answerId, questionId)
                .orElseThrow(NotFoundException::new);
        if (!answer.matchesWriter(user)) {
            throw new UnauthorizedAccessException("다른 사람의 답변을 수정하거나 삭제할 수 없습니다.");
        }
        return answer;
    }
}
