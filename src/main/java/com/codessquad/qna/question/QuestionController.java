package com.codessquad.qna.question;

import com.codessquad.qna.user.UserDTO;
import com.codessquad.qna.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping
    public ModelAndView getQuestions() {
        return new ModelAndView("/qna/list", "questions", questionService.getQuestions());
    }

    @PostMapping
    public String createQuestions(Question question, HttpSession session) {
        questionService.createQuestion(question, SessionUtils.getSessionUser(session).toDTO());

        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView getQuestion(@PathVariable Long id) {
        return new ModelAndView("/qna/show", "question", questionService.getQuestion(id));
    }

    @GetMapping("/{id}/form")
    public ModelAndView getQuestionUpdateForm(@PathVariable Long id, HttpSession session) {
        UserDTO sessionUser = SessionUtils.getSessionUser(session).toDTO();

        return new ModelAndView("/qna/updateForm", "question", questionService.getQuestion(id, sessionUser));
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question newQuestion, HttpSession session) {
        questionService.updateQuestion(id, newQuestion, SessionUtils.getSessionUser(session).toDTO());

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        questionService.deleteQuestion(id, SessionUtils.getSessionUser(session).toDTO());

        return "redirect:/questions";
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setQuestion(questionService.getQuestion(questionId));
        answer.setWriter(SessionUtils.getSessionUser(session));

        answerService.createAnswer(answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        answerService.deleteAnswer(id, SessionUtils.getSessionUser(session).toDTO());

        return "redirect:/questions/" + questionId;
    }
}
