package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.exception.CrudNotAllowedException;
import com.codessquad.qna.web.exception.EntityNotFoundException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void create(long questionId, String contents, HttpSession session){
        User loginUser = SessionUtils.getLoginUser(session);

        Question question = getQuestionById(questionId);

        Answer answer = new Answer.Builder()
                .writer(loginUser)
                .question(question)
                .contents(contents)
                .build();

        answerRepository.save(answer);
    }

    public void delete(long questionId, long id, HttpSession session){
        User loginUser = SessionUtils.getLoginUser(session);
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found answer number " + id));

        if (!answer.isMatchingWriter(loginUser)) {
            throw new CrudNotAllowedException("Only writer can delete this answer post!");
        }
        answerRepository.delete(answer);
    }

    private Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found question number " + id));
    }
}
