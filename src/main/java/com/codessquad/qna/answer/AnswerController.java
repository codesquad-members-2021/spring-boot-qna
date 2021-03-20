package com.codessquad.qna.answer;

import com.codessquad.qna.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{id}/form")
    public ModelAndView viewUpdateForm(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        AnswerDTO result = answerService.readVerifiedAnswer(id, SessionUtils.getSessionUser(session));

        return new ModelAndView("/qna/answerUpdateForm", "answer", result);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long questionId, @PathVariable Long id, AnswerDTO newAnswer, HttpSession session) {
        newAnswer.setWriter(SessionUtils.getSessionUser(session));

        answerService.update(id, newAnswer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        answerService.delete(id, SessionUtils.getSessionUser(session));

        return "redirect:/questions/" + questionId;
    }
}
