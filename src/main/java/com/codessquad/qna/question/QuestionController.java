package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
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
        long questionWriterId = question.getWriter().getId().longValue();

        User sessionUser = ((User) session.getAttribute("sessionedUser"));
        if (sessionUser == null) {
            return "redirect:/users/login/form";
        }
        long sessionUserId = sessionUser.getId().longValue();

        if (questionWriterId != sessionUserId) {
            throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
        }

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

        long questionWriterId = question.getWriter().getId().longValue();

        User sessionUser = ((User) session.getAttribute("sessionedUser"));
        if (sessionUser == null) {
            return new ModelAndView("redirect:/users/login/form");
        }
        long sessionUserId = sessionUser.getId().longValue();

        if (questionWriterId != sessionUserId) {
            throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
        }
        return new ModelAndView("/qna/updateForm", "question", question);
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question newQuestion, HttpSession session) {
        Question existedQuestion = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        long questionWriterId = existedQuestion.getWriter().getId().longValue();

        User sessionUser = ((User) session.getAttribute("sessionedUser"));
        if (sessionUser == null) {
            return "redirect:/users/login/form";
        }
        long sessionUserId = sessionUser.getId().longValue();

        if (questionWriterId != sessionUserId) {
            throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
        }

        existedQuestion.update(newQuestion);
        questionRepository.save(existedQuestion);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        long questionWriterId = question.getWriter().getId().longValue();

        User sessionUser = ((User) session.getAttribute("sessionedUser"));
        if (sessionUser == null) {
            return "redirect:/users/login/form";
        }
        long sessionUserId = sessionUser.getId().longValue();

        if (questionWriterId != sessionUserId) {
            throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
        }

        questionRepository.delete(question);

        return "redirect:/questions";
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        User sessionUser = (User) session.getAttribute("sessionedUser");

        if (sessionUser == null) {
            return "redirect:/users/login/form";
        }

        answer.setWriter(sessionUser);
        answer.setQuestion(question);

        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }
}
