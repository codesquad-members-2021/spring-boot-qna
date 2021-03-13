package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import com.codessquad.qna.user.UserDTO;
import com.codessquad.qna.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.questionService = questionService;
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
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        User sessionUser = SessionUtils.getSessionUser(session);

        answer.setWriter(sessionUser);
        answer.setQuestion(question);

        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));

        SessionUtils.verifyWithSessionUser(session, answer.getWriter());

        answerRepository.deleteById(id);

        return "redirect:/questions/" + questionId;
    }
}
