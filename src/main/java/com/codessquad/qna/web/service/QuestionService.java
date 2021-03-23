package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.exception.CrudNotAllowedException;
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

    public void create(HttpSession session, QuestionRequest request){
        User loginUser = SessionUtils.getLoginUser(session);
        Question question = request.toEntity(loginUser);
        questionRepository.save(question);
    }

    public void update(long questionId, HttpSession session, QuestionRequest request){
        Question question = authenticate(questionId, session);
        question.update(request.getTitle(), request.getContents());
        questionRepository.save(question);
    }


    public void delete(long questionId, HttpSession session){
        Question question = authenticate(questionId, session);
        questionRepository.delete(question);
    }

    public Question authenticate(long questionId, HttpSession session){
        Question question = getQuestionById(questionId);
        User writer = question.getWriter();
        User loginUser = SessionUtils.getLoginUser(session);

        if (!loginUser.isMatchingWriter(writer)) {
            throw new CrudNotAllowedException("Cannot edit other user's posts");
        }
        return question;
    }
    public List<Answer> list(long questionId){
        return answerRepository.findByQuestionId(questionId);
    }

    public Question getQuestionById(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found question number " + questionId));
    }
}
