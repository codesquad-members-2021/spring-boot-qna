package com.codessquad.qna.web.qna;

import com.codessquad.qna.web.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QnaController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = (User) session.getAttribute(User.SESSION_KEY_USER_OBJECT);
        if (sessionUser != null) {
            newQuestion.setWriterId(sessionUser.getId());
            newQuestion.setWriterUserId(sessionUser.getUserId());
            questionRepository.save(newQuestion);
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String getOneQuestion(@PathVariable("questionId") long questionId, Model model) {
        Question foundQuestion = questionRepository.findById(questionId).orElse(null);
        model.addAttribute("question", foundQuestion);
        return "qna/show";
    }

}
