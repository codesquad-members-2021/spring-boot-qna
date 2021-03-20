package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
    }

    public void checkValid(Question question, User user) {
        if (!question.isPostWriter(user)) {
            throw new IllegalStateException("자신의 질문만 접근 가능합니다.");
        }
    }

    public void update(Question question, Question newQuestion, User user) {
        checkValid(question, user);
        question.update(newQuestion);
        questionRepository.save(question);
    }

    public void delete(Question question, User user) {
        checkValid(question, user);
        if (!question.isAnswerWriterSame()) {
            // Todo: 예외 발생을 해야하나.. 새로운 예외 페이지도 만들어야하나..
        }
        if (question.isAnswerEmpty()) {
            question.setDeletedTrue();
            question.deleteAnswers();
            questionRepository.save(question);
        }
    }
}
