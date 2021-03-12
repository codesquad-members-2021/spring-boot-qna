package com.codessquad.qna.web.questions;

import com.codessquad.qna.web.exceptions.QuestionNotFoundException;
import com.codessquad.qna.web.users.User;
import com.codessquad.qna.web.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionsController {

    Logger logger = LoggerFactory.getLogger(QuestionsController.class);

    private final QuestionRepository questionRepository;

    public QuestionsController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        newQuestion.setWriter(sessionUser);
        questionRepository.save(newQuestion);
        logger.info("question created! [" + newQuestion.getId() + "] " + " title : " + newQuestion.getTitle());
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String getOneQuestion(@PathVariable("questionId") long questionId, Model model) {
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        model.addAttribute("question", foundQuestion);
        model.addAttribute("numberOfAnswers", foundQuestion.getSizeOfAnswers());
        return "qna/show";
    }

    @GetMapping("/questions/modify/{questionId}")
    public String getModifyPage(@PathVariable("questionId") long questionId,
                                Model model, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        if (!currentQuestion.isMatchingWriterId(sessionUser.getId())) {
            return "redirect:/";
        }
        model.addAttribute("currentQuestion", currentQuestion);
        return "qna/modify-form";
    }

    @PutMapping("/questions/modify")
    public String modifyQuestion(long questionId, String newTitle, String newContents, HttpSession session) {
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        if (currentQuestion == null) {
            return "redirect:/";
        }
        User sessionUser = SessionUtil.getLoginUser(session);
        if (!currentQuestion.isMatchingWriterId(sessionUser.getId())) {
            return "redirect:/";
        }
        currentQuestion.setTitle(newTitle);
        currentQuestion.setContents(newContents);
        questionRepository.save(currentQuestion);
        logger.info("question modified! title : " + currentQuestion.getTitle());
        return "redirect:/questions/" + currentQuestion.getId();
    }

    @DeleteMapping("/questions/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") long questionId, HttpSession session) {
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        if (currentQuestion == null) {
            return "redirect:/";
        }
        User sessionUser = SessionUtil.getLoginUser(session);
        if (!currentQuestion.isMatchingWriterId(sessionUser.getId())) {
            return "redirect:/";
        }
        questionRepository.delete(currentQuestion);
        logger.info("question deleted! title : " + currentQuestion.getTitle());
        return "redirect:/";
    }

}
