package com.codessquad.qna.service;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Answer create(Long questionId, Answer answer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("해당 질문이 없습니다. id = " + questionId));
        answer.setQuestion(question);
        question.addAnswer(answer);
        return answerRepository.save(answer);
    }

    @Transactional
    public Answer findById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 답변이 없습니다. id = " + id));
    }

    @Transactional
    public List<Answer> findAllByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionIdAndDeletedIsFalse(questionId);
    }

    @Transactional
    public Long update(Long id, Answer answerWithUpdateInfo) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 답변이 없습니다. id = " + id));
        answer.update(answerWithUpdateInfo);

        return id;
    }

    @Transactional
    public Long deleteById(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 답변이 없습니다. id = " + id));
        Question question = answer.getQuestion();
        answer.delete();
        question.downCountOfAnswer();

        return id;
    }
}
