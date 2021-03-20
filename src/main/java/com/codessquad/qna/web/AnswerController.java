package com.codessquad.qna.web;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
        Answer answer = new Answer(loggedinUser, question, contents);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long answerId, Long questionId, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("해당 답변이 존재하지 않습니다."));
        if (!answer.isAnswerWriter(loggedinUser)) {
            throw new IllegalStateException("자신의 댓글에만 접근 가능합니다.");
        }
        answerRepository.delete(answer);
        return "redirect:/questions/{questionId}";
    }
}
