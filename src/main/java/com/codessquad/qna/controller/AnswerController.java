package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.exception.type.UnauthorizedException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by 68936@naver.com on 2021-03-16 오전 2:28
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@Controller
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    @Autowired
    public AnswerController(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @PostMapping("/questions/{question.id}/answers")
    public String createAnswer(Answer answer, HttpSession session) {
        HttpSessionUtils.checkValidOf(session);

        User findUser = HttpSessionUtils.getLoginUserOf(session);
        answer.setReplyId(findUser.getUserId());
        answer.setReplyAuthor(findUser.getName());
        answerRepository.save(answer);
        return "redirect:/questions/{question.id}";
    }

    @DeleteMapping("/questions/{question.id}/answers/{answerId}")
    public String deleteAnswer(HttpSession session, @PathVariable("answerId") Long id) {
        ValidUtils.checkIllegalArgumentOf(id);
        HttpSessionUtils.checkValidOf(session);

        User loginUser = HttpSessionUtils.getLoginUserOf(session);
        Answer selectedAnswer = answerRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!Objects.equals(loginUser.getUserId(), selectedAnswer.getReplyId())) {
            throw new UnauthorizedException();
        }

        if(!selectedAnswer.isAnswerDeleted()){   // soft delete
            selectedAnswer.setAnswerDeleted(true);
        }
        answerRepository.save(selectedAnswer); // soft delete

        return "redirect:/questions/{question.id}";
    }

    @GetMapping("/questions/{question.id}/answers/{answerId}/update")
    public String modifyAnswerButton(@PathVariable("question.id") Long targetId, @PathVariable Long answerId, Model model) {
        Question questionData = questionService.findById(targetId); // findById() 내부에서 인자 예외처리

        for(Answer answer : questionData.getAnswers()){
            if (Objects.equals(answer.getAnswerId(),answerId)){
                model.addAttribute("answer", answer); // 수정한 댓글만 가져온다.
                model.addAttribute("question", questionData);
                return "qna/updateShow";
            }
        }
        throw new NotFoundException();
    }

    @PutMapping("/questions/{question.id}/answers/{answerId}")
    public String updateAnswer(HttpSession session, @PathVariable("answerId") Long id, String replyContents) {
        ValidUtils.checkIllegalArgumentOf(id);
        HttpSessionUtils.checkValidOf(session);

        User loginUser = HttpSessionUtils.getLoginUserOf(session);
        Answer selectedAnswer = answerRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!Objects.equals(loginUser.getUserId(), selectedAnswer.getReplyId())) {
            throw new UnauthorizedException();
        }
        selectedAnswer.setReplyContents(replyContents);
        answerRepository.save(selectedAnswer);
        return "redirect:/questions/{question.id}";
    }

}
