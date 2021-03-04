package com.codessquad.qna.controller;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRequest;
import com.codessquad.qna.user.User;
import com.codessquad.qna.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class QuestionController {

    private final UserRepository userRepository;
    private final List<Question> questions = new ArrayList<>();
    private final Pattern questionIdPattern = Pattern.compile("[1-9]\\d*");

    @Autowired
    public QuestionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/questions")
    public String query(QuestionRequest questionRequest) {
        String writerId = questionRequest.getWriter();
        Optional<User> optionalWriter = userRepository.findUserById(writerId);
        User writer = optionalWriter.orElse(new User(writerId, writerId));
        Question question = new Question();
        int questionId = questions.size() + 1;
        question.setId(questionId);
        question.setWriter(writer);
        question.setTitle(questionRequest.getTitle());
        question.setContents(questionRequest.getContents());
        question.setTime(LocalDateTime.now());
        question.setPoint(0);
        questions.add(question);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/questions/{id}")
    public String qnaShow(@PathVariable("id") String id, Model model) {
        Matcher questionIdMatcher = questionIdPattern.matcher(id);
        if (!questionIdMatcher.matches()) {
            return "redirect:/";
        }
        int questionIndex = Integer.parseInt(id) - 1;
        if (questionIndex < questions.size()) {
            model.addAttribute(questions.get(questionIndex));
            return "qna/show";
        }
        return "redirect:/";
    }

}
