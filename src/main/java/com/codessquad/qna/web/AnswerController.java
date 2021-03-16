package com.codessquad.qna.web;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerController(QuestionRepository questionRepository,
                            AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/")
    public String createAnswer(@PathVariable Long questionId, Answer answer,
                               HttpSession session) {
        User writer = (User) session.getAttribute("sessionedUser");
        Question question = questionRepository.findById(questionId).get();

        answer.setQuestion(question);
        answer.setWriter(writer);
        answerRepository.save(answer);

        logger.info("answer : {}", answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId,
                         @PathVariable Long id,
                         HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Optional<Answer> answer = answerRepository.findById(id);

        if(!answer.get().matchWriter(loginedUser)) {
            throw new IllegalStateException("본인만 작성할 수 있습니다.");//TODO customize 하기
        }

        answerRepository.delete(answer.get());
        questionRepository.findById(questionId).get();

        return "redirect:/questions/" + questionId;
    }
}
