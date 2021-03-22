package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.QuestionRepository;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionsController.class);

    private final QuestionRepository questionRepository;

    public QuestionsController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        newQuestion.setWriter(sessionUser);
        questionRepository.save(newQuestion);
        LOGGER.info("question created {}", newQuestion);
        return "redirect:/";
    }

    @GetMapping
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAllByDeletedFalse());
        return "index";
    }

    @GetMapping("/{questionId}")
    public String getOneQuestion(@PathVariable("questionId") long questionId, Model model) {
        Question foundQuestion = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        model.addAttribute("question", foundQuestion);
        return "qna/show";
    }

    @GetMapping("/{questionId}/modify-form")
    public String getModifyPage(@PathVariable("questionId") long questionId,
                                Model model, HttpSession session) {
        Question currentQuestion = verifyQuestionAndGet(session, questionId);
        model.addAttribute("currentQuestion", currentQuestion);
        return "qna/modify-form";
    }

    @PutMapping("/{questionId}")
    public String modifyQuestion(@PathVariable("questionId") long questionId,
                                 String newTitle, String newContents, HttpSession session) {
        Question currentQuestion = verifyQuestionAndGet(session, questionId);
        currentQuestion.update(newTitle, newContents);
        questionRepository.save(currentQuestion);
        LOGGER.info("question modified {}", currentQuestion);
        return "redirect:/questions/" + currentQuestion.getId();
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") long questionId, HttpSession session) {
        Question currentQuestion = verifyQuestionAndGet(session, questionId);
        questionRepository.delete(currentQuestion);
        LOGGER.info("question deleted {}", currentQuestion);
        return "redirect:/";
    }

    private Question verifyQuestionAndGet(HttpSession session, Long questionId) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question currentQuestion = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        verifyAuthorizedAccess(currentQuestion, sessionUser);
        return currentQuestion;
    }

    private void verifyAuthorizedAccess(Question question, User loginUser) {
        if (!question.isMatchingWriter(loginUser)) {
            throw new UnauthorizedAccessException("자신이 작성한 질문만 수정 혹은 삭제할 수 있습니다");
        }
    }
}

