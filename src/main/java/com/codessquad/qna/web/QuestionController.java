package com.codessquad.qna.web;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.domain.user.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.domain.user.HttpSessionUtils.isLoginUser;

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
        return isLoginUser(session) ?
                "qna/form" : "redirect:/users/loginForm";
    }

    @PostMapping("/")
    public String submitQuestion(Question question, Model model, HttpSession session) {
        User loginedUser = getUserFromSession(session);

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
        if (!isLoginUser(session)) {
            return "/users/loginForm";
        }

        Question question = questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
        List<Answer> answers = answerRepository.findByQuestion(question);

        model.addAttribute("question", question);
        model.addAttribute("answerList", answers);
        logger.info("question : {} ", question);
        logger.info("answer : {} ", answers);

        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginedUser = getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);

        if (!loginedUser.matchUser(question.getWriter())) {
            throw new IllegalUserAccessException();
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question updateQuestion, HttpSession session) {
        User loginedUser = getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);

        if (!loginedUser.matchUser(question.getWriter())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        question.update(updateQuestion);
        questionRepository.save(question);

        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginedUser = getUserFromSession(session);
        Question question = questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);

        if (!question.isSameWriter(loginedUser)) {
            throw new IllegalUserAccessException();
        }

        questionRepository.deleteById(id);

        return "redirect:/";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    public String handleIllegalUserAccessException(@PathVariable Long questionId) {
        return "redirect:/" + questionId;
    }
}
