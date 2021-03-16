package com.codessquad.qna.web;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionController(QuestionRepository questionRepository,
                              AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/form")
    public String getQuestionFormPage(HttpSession session) {
        User user = (User) session.getAttribute("sessionedUser");

        if (user == null) {
            return "redirect:/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("/")
    public String submitQuestion(Question question, Model model, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        question.setWriter(loginedUser);

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

    @GetMapping("/{questionId}")
    public String getQuestionDetail(@PathVariable(("questionId")) Long id, Model model, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Optional<Question> question = questionRepository.findById(id);
        List<Answer> answers = answerRepository.findByQuestion(question.get());

        if (loginedUser == null) {
            return "/users/loginForm";
        }

        if (!question.isPresent()) {
            return "redirect:/";
        }

        model.addAttribute("question", question.get());
        model.addAttribute("answerList", answers);
        logger.info("question : {} ", question);
        logger.info("answer : {} ", answers);

        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Question question = questionRepository.findById(id).get();

        if (loginedUser == null) {
            throw new IllegalStateException("로그인되지 않았습니다.");
        }

        if (!loginedUser.matchUser(question.getWriter())) {
            throw new IllegalStateException("른 사람의 글을 수정할 수 없다");
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question updateQuestion, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Question question = questionRepository.findById(id).get();

        if (loginedUser == null) {
            throw new IllegalStateException("로그인되지 않았습니다.");
        }

        if (!loginedUser.matchUser(question.getWriter())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        question.update(updateQuestion);
        questionRepository.save(question);

        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginedUser = (User) session.getAttribute("sessionedUser");
        Optional<Question> question = questionRepository.findById(id);

        if (!question.get().isSameWriter(loginedUser)) {
            return "redirect:/";
        }

        questionRepository.deleteById(id);

        return "redirect:/";
    }

}
