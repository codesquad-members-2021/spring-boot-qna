package com.codessquad.qna.web;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.domain.user.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.domain.user.HttpSessionUtils.isLoggedInUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService,
                              AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/form")
    public String getQuestionFormPage(HttpSession session) {
        return isLoggedInUser(session) ?
                "qna/form" : "redirect:/users/loginForm";
    }

    @PostMapping("/")
    public String submitQuestion(String title, String content,
                                 HttpSession session) {
        User loginedUser = getUserFromSession(session);
        Question question = new Question(loginedUser, title, content);
        questionService.createQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionListPage(Model model) {
        model.addAttribute("questionList", questionService.findUnRemovedList());
        return "home";
    }

    @GetMapping("/{questionId}")
    public String getQuestionDetail(@PathVariable(("questionId")) Long id, Model model, HttpSession session) {
        if (!isLoggedInUser(session)) {
            throw new UserNotFoundException();
        }

        Question question = questionService.findById(id);
        List<Answer> answers = answerService.findAnswerListByQuestion(question);

        model.addAttribute("question", question);
        model.addAttribute("answerList", answers);

        logger.debug("question : {} ", question);
        logger.debug("answer : {} ", answers);

        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loggedInUser = getUserFromSession(session);
        Question question = questionService.findById(id);

        if (!loggedInUser.matchUser(question.getWriter())) {
            throw new IllegalUserAccessException();
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question updateQuestion, HttpSession session) {
        User loggedInUser = getUserFromSession(session);
        Question question = questionService.findById(id);

        if (!loggedInUser.matchUser(question.getWriter())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        questionService.update(question, updateQuestion);

        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loggedInUser = getUserFromSession(session);
        Question question = questionService.findById(id);

        if (!question.isSameWriter(loggedInUser)) {
            throw new IllegalUserAccessException();
        }

        questionService.deleteBy(id, question);

        return "redirect:/";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException() {
        return "redirect:/";
    }
}
