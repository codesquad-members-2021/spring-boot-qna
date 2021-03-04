package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("questions")
public class QuestionController {
    private List<Question> questions = Collections.synchronizedList(new ArrayList<>(Arrays.asList(
            new Question("자바지기",
                     "국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                    "test contents",
                    LocalDateTime.of(2016, 01, 15, 18, 47)),
            new Question("김문수",
                    "runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?",
                    "test contents",
                    LocalDateTime.of(2016, 01, 05, 18, 47))
    )));

    @GetMapping
    public ModelAndView getQuestions() {
        return new ModelAndView("/qna/list", "questions", questions);
    }

    @PostMapping
    public String createQuestions(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }
}
