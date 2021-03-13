package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class QuestionController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    private String questions(Question question, Model model, HttpSession session) {
        Objects.requireNonNull(question, "Exception: question이 NULL 값입니다.");
        final Object sessionValue = session.getAttribute("sessionedUser");
        if(sessionValue != null) {
            User user = (User)sessionValue;
            question.setWriter(user.getName());
            questionRepository.save(question);
            return "redirect:/";
        }
        model.addAttribute("errorMessage","로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }

    @GetMapping("/questions/{id}")
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        Objects.requireNonNull(targetId, "Exception: targetKey가 NULL 값입니다.");

        Optional<Question> questionOptional = questionRepository.findById(targetId);
        Question questionData = questionOptional.orElseThrow(NoSuchElementException::new);

        model.addAttribute("question", questionData);

        return "qna/show";
    }


}
