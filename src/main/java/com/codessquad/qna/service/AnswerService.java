package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.DisplayStatus;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.dto.AnswerDto;
import com.codessquad.qna.domain.dto.UserDto;
import com.codessquad.qna.exception.NoSearchObjectException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public AnswerDto write(UserDto writer, String contents, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSearchObjectException("질문"));
        Answer answer = new Answer(writer.returnEntity(), question, contents);
        return AnswerDto.createDto(answerRepository.save(answer));
    }

    private Answer findById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new NoSearchObjectException("답변"));
    }

    public AnswerDto findByIdToDto(Long answerId) {
        return AnswerDto.createDto(findById(answerId));
    }

    @Transactional
    public Long delete(Long id, UserDto user) {
        Answer answer = findById(id);
        answer.checkSameUser(user.getId());
        answer.deleteAnswer(DisplayStatus.CLOSE);
        return id;
    }
}
