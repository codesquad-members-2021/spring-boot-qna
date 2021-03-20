package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.exception.CRUDAuthenticationException;
import com.codessquad.qna.web.exception.EntityNotFoundException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void create(QuestionRequest request, HttpSession session){
        User loginUser = SessionUtils.getLoginUser(session);
        Question question = Question.toEntity(loginUser, request);
        questionRepository.save(question);
    }

    public void update(long id, QuestionRequest request){
        Question question = getQuestionById(id);
        question.update(request.getTitle(), request.getContents());
        questionRepository.save(question);
    }

    public Question authenticate(long id, HttpSession session){
        Question question = getQuestionById(id);
        User writer = question.getWriter();
        User loginUser = SessionUtils.getLoginUser(session);

        if (!loginUser.isMatchingWriter(writer)) {
            throw new CRUDAuthenticationException("Cannot edit other user's posts");
        }
        return question;
    }

    public void delete(long id, HttpSession session){
        Question question = getQuestionById(id);
        User loginUser = SessionUtils.getLoginUser(session);

        if (!question.isMatchingWriter(loginUser)) {
            throw new CRUDAuthenticationException("Cannot edit other user's posts");
        }
        questionRepository.delete(question);
    }

    public List<Answer> list(long questionId){
        return answerRepository.findByQuestionId(questionId);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found question number " + id));
    }
}
