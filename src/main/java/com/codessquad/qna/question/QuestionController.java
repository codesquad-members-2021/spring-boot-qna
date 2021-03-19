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
    public ModelAndView readAll() {
        return new ModelAndView("/qna/list", "questions", questionService.readAll());
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        question.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.create(question);

        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView read(@PathVariable Long id) {
        return new ModelAndView("/qna/show", "question", questionService.read(id));
    }

    @GetMapping("/{id}/form")
    public ModelAndView viewUpdateForm(@PathVariable Long id, HttpSession session) {
        Question result = questionService.readVerifiedQuestion(id, SessionUtils.getSessionUser(session));

        return new ModelAndView("/qna/updateForm", "question", result);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question newQuestion, HttpSession session) {
        newQuestion.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.update(id, newQuestion);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        questionService.delete(id, SessionUtils.getSessionUser(session));

        return "redirect:/questions";
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setQuestion(questionService.read(questionId));
        answer.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.createAnswer(answer);

        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{questionId}/answers/{id}/form")
    public ModelAndView viewAnswerUpdateForm(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Answer result = questionService.readVerifiedAnswer(id, SessionUtils.getSessionUser(session));

        return new ModelAndView("/qna/answerUpdateForm", "answer", result);
    }

    @PutMapping("/{questionId}/answers/{id}")
    public String updateAnswer(@PathVariable Long questionId, @PathVariable Long id, Answer newAnswer, HttpSession session) {
        newAnswer.setWriter(SessionUtils.getSessionUser(session).toEntity());

        questionService.updateAnswer(id, newAnswer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        questionService.deleteAnswer(id, SessionUtils.getSessionUser(session));

        return "redirect:/questions/" + questionId;
    }
}
