package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QnaController {
    private final QuestionService questionService;

    public QnaController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        questionService.save(question, getUserFromSession(session));
        return "redirect:/";
    }

    @GetMapping
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionService.listAllQuestions());
        return "index";
    }

    @GetMapping("/{id}")
    public String getDetailedQuestion(@PathVariable long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, HttpSession session, Model model) {
        Question question = questionService.findById(id);
        User user = getUserFromSession(session);
        if (!question.getWriter().equals(user.getUserId())) { // 로그인 된 유저와 질문 작성자 불일치 시
            // TODO: throw exception
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}/modify")
    public String modifyQuestion(@PathVariable long id, HttpSession session, Question modifiedQuestion) {
        Question originalQuestion = questionService.findById(id);
        User user = getUserFromSession(session);
        if (!originalQuestion.getWriter().equals(user.getUserId())) { // 작성자와 로그인 된 사용자가 불일치 시
            // TODO: throw exception "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동하도록
        }
        questionService.modifyQuestion(originalQuestion, modifiedQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        Question question = questionService.findById(id);
        User user = getUserFromSession(session);
        if (!question.getWriter().equals(user.getUserId())) {
            // TODO: 로그인 안 됐으면 로그인 화면 이동, 다른 유저면 에러페이지
            System.out.println("not logged in");
            return "redirect:/users/login";
        }
        questionService.deleteQuestion(question);
        return "redirect:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException() {
        return "error";
    }
}
