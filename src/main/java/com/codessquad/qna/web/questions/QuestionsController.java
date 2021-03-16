package com.codessquad.qna.web.questions;

import com.codessquad.qna.web.answers.Answer;
import com.codessquad.qna.web.answers.AnswersRepository;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionsController.class);

    private final QuestionRepository questionRepository;
    private final AnswersRepository answersRepository;

    public QuestionsController(QuestionRepository questionRepository,
                               AnswersRepository answersRepository) {
        this.questionRepository = questionRepository;
        this.answersRepository = answersRepository;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        newQuestion.setWriter(sessionUser);
        questionRepository.save(newQuestion);
        LOGGER.info("question created " + newQuestion);
        return "redirect:/";
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);

        Question targetQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        answersRepository.save(new Answer(answerContents, targetQuestion, sessionUser));
        return "redirect:/questions/" + questionId;
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
        validateAuthorizedAccess(currentQuestion, sessionUser);

        model.addAttribute("currentQuestion", currentQuestion);
        return "qna/modify-form";
    }

    @PutMapping("/{questionId}")
    public String modifyQuestion(@PathVariable("questionId") long questionId,
                                 String newTitle, String newContents, HttpSession session) {
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        User sessionUser = SessionUtil.getLoginUser(session);
        validateAuthorizedAccess(currentQuestion, sessionUser);
        currentQuestion.update(newTitle, newContents);

        questionRepository.save(currentQuestion);
        LOGGER.info("question modified " + currentQuestion);
        return "redirect:/questions/" + currentQuestion.getId();
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") long questionId, HttpSession session) {
        Question currentQuestion = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        User sessionUser = SessionUtil.getLoginUser(session);
        validateAuthorizedAccess(currentQuestion, sessionUser);

        questionRepository.delete(currentQuestion);
        LOGGER.info("question deleted " + currentQuestion);
        return "redirect:/";
    }

    private void validateAuthorizedAccess(Question currentQuestion, User loginUser) {
        if (!currentQuestion.isMatchingWriter(loginUser)) {
            throw new UnauthorizedAccessException();
        }
    }
}
