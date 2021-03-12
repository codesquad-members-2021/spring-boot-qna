package com.codessquad.qna.web;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/form")
    public String getQuestionFormPage(HttpSession session) {
        User user = (User) session.getAttribute("sessionedUser");

        if(user == null) {
            return "redirect:/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("/")
    public String submitQuestion(Question question, Model model) {
        questionRepository.save(question);
        model.addAttribute(question);

        logger.debug("question : {} ", question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionListPage(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "home";
    }

    @GetMapping("/{index}")
    public String getQuestionDetail(@PathVariable(("index")) long index, Model model, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Optional<Question> question = questionRepository.findById(index);

        if(loginedUser == null) {
            return "/users/loginForm";
        }

        if (!question.isPresent()) {
            return "redirect:/";
        }

        model.addAttribute("question", question.get());

        logger.debug("question : {} ", question);

        return "/qna/show";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Optional<Question> question = questionRepository.findById(id);

        if(!question.get().isSameWriter(loginedUser.getUserId())) {
            return "redirect:/";
        }

        questionRepository.deleteById(id);

        return "redirect:/";
    }

}
