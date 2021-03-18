package com.codessquad.qna.question;

import com.codessquad.qna.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ModelAndView readQuestions() {
        return new ModelAndView("/qna/list", "questions", questionService.readQuestions());
    }

    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        question.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.createQuestion(question);

        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView readQuestion(@PathVariable Long id) {
        return new ModelAndView("/qna/show", "question", questionService.readQuestion(id));
    }

    @GetMapping("/{id}/form")
    public ModelAndView readQuestionUpdateForm(@PathVariable Long id, HttpSession session) {
        Question result = questionService.readVerifiedQuestion(id, SessionUtils.getSessionUser(session));

        return new ModelAndView("/qna/updateForm", "question", result);
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question newQuestion, HttpSession session) {
        newQuestion.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.updateQuestion(id, newQuestion);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        questionService.deleteQuestion(id, SessionUtils.getSessionUser(session));

        return "redirect:/questions";
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setQuestion(questionService.readQuestion(questionId));
        answer.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.createAnswer(answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        questionService.deleteAnswer(id, SessionUtils.getSessionUser(session));

        return "redirect:/questions/" + questionId;
    }
}
