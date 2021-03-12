package com.codessquad.qna.web.questions;

import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.users.User;
import com.codessquad.qna.web.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    Logger logger = LoggerFactory.getLogger(QuestionsController.class);

    private final QuestionRepository questionRepository;

    public QuestionsController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        newQuestion.setWriter(sessionUser);
        questionRepository.save(newQuestion);
        logger.info("question created " + newQuestion);
        return "redirect:/";
    }

    @GetMapping
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/{questionId}")
    public String getOneQuestion(@PathVariable("questionId") long questionId, Model model) {
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        model.addAttribute("question", foundQuestion);
        model.addAttribute("numberOfAnswers", foundQuestion.getSizeOfAnswers());
        return "qna/show";
    }

    @GetMapping("/{questionId}/modify-form")
    public String getModifyPage(@PathVariable("questionId") long questionId,
                                Model model, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        if (!currentQuestion.isMatchingWriter(sessionUser)) {
            throw new UnauthorizedAccessException();
        }
        model.addAttribute("currentQuestion", currentQuestion);
        return "qna/modify-form";
    }

    @PutMapping("/{questionId}")
    public String modifyQuestion(@PathVariable("questionId") long questionId,
                                 String newTitle, String newContents, HttpSession session) {
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        User sessionUser = SessionUtil.getLoginUser(session);
        if (!currentQuestion.isMatchingWriter(sessionUser)) {
            return "redirect:/";
        }
        currentQuestion.update(newTitle, newContents);
        questionRepository.save(currentQuestion);
        logger.info("question modified " + currentQuestion);
        return "redirect:/questions/" + currentQuestion.getId();
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") long questionId, HttpSession session) {
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        User sessionUser = SessionUtil.getLoginUser(session);
        if (!currentQuestion.isMatchingWriter(sessionUser)) {
            return "redirect:/";
        }
        questionRepository.delete(currentQuestion);
        logger.info("question deleted " + currentQuestion);
        return "redirect:/";
    }

}
