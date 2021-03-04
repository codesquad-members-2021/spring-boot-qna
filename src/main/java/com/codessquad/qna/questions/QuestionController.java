package com.codessquad.qna.questions;

import com.codessquad.qna.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    @PostMapping
    String createQuestion(Question question) {
        question.setDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        synchronized (questions) {
            question.setIndex(questions.size() + 1);
            questions.add(question);
        }

        return "redirect:/";
    }
}
