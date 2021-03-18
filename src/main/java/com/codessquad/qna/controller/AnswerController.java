package com.codessquad.qna.controller;

import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by 68936@naver.com on 2021-03-16 오전 2:28
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@Controller
public class AnswerController {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    //@PathVariable("question.id") Long questionId, @PathVariable("id") Long id, Model model
    @PostMapping("/questions/{question.id}/answers")
    public String createAnswer(Answer answer, HttpSession session, Model model) {
        HttpSessionUtils.checkValidOf(session);

        User findUser = HttpSessionUtils.getLoginUserOf(session);
        answer.setReplyId(findUser.getUserId());
        answer.setReplyAuthor(findUser.getName());
        answerRepository.save(answer);
        return "redirect:/questions/{question.id}";
    }

}
