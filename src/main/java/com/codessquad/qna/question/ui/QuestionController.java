package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.codessquad.qna.common.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(@Valid QuestionRequest questionRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        questionService.save(questionRequest, writer);
        return "redirect:/questions";
    }

    @GetMapping("form")
    public String getQuestionForm(HttpSession session) {
        checkLoggedIn(session);
        return "question/form";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "question/list";
    }

    @GetMapping("{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        return "question/show";
    }

    @GetMapping("{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        return "question/updateForm";
    }

    @PutMapping("{id}")
    public String updateQuestion(@PathVariable Long id, @Valid QuestionRequest questionRequest, HttpSession session) {
        checkQuestionAuthorization(id, session);
        User writer = getUserAttribute(session);
        questionService.updateQuestion(id, questionRequest, writer);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        checkQuestionAuthorization(id, session);
        questionService.deleteQuestion(id);
        return String.format("redirect:/questions", id);
    }

    // FIXME: 권한 인증과 관련된 부분은 AOP 로 분리해야한다.
    private void checkQuestionAuthorization(Long id, HttpSession session) {
        User writer = questionService.getWriter(id);
        checkAuthorization(writer, session);
        /**
         * NOTE: UserController 에서의 권한 인증과 동일한 로직을 사용하기 위해
         * 포비 영상에서 Question 도메인에 isSameWriter 메서드를 구현한 것과 다르게,
         * 일부러 getWriterId 라는 게터를 questionService 에 정의하였다.
         * 권한 로직을 AOP 로 분리하기 위해서라도, 권한 인증 로직은 동일한 메서드를 활용하도록 할 예정이다.
         */
    }
}
