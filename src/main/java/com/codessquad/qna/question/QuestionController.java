package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import com.codessquad.qna.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping
    public ModelAndView getQuestions() {
        return new ModelAndView("/qna/list", "questions", questionRepository.findAll());
    }

    @PostMapping
    public String createQuestions(Question question, HttpSession session) {
        SessionUtils.verifyWithSessionUser(session, question.getWriter());

        questionRepository.save(question);

        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView getQuestion(@PathVariable Long id) {
        Question question = questionRepository.findById(id).get();
        return new ModelAndView("/qna/show", "question", question);
    }

    @GetMapping("/{id}/form")
    public ModelAndView getQuestionUpdateForm(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        SessionUtils.verifyWithSessionUser(session, question.getWriter());

        return new ModelAndView("/qna/updateForm", "question", question);
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question newQuestion, HttpSession session) {
        Question existedQuestion = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        SessionUtils.verifyWithSessionUser(session, existedQuestion.getWriter());

        existedQuestion.update(newQuestion);
        questionRepository.save(existedQuestion);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        SessionUtils.verifyWithSessionUser(session, question.getWriter());

        questionRepository.delete(question);

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
