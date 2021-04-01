package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.dto.AnswerDto;
import com.codessquad.qna.model.dto.QuestionDto;
import com.codessquad.qna.model.dto.UserDto;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Service;


@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void save(Long questionId, AnswerDto answerDto, UserDto sessionedUserDto) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        answerDto.save(sessionedUserDto, new QuestionDto(question));
        answerRepository.save(answerDto.toEntity());
    }

    public void update(Long answerId, AnswerDto updatedAnswerDto, UserDto sessionedUserDto) {
        AnswerDto answerDto = getAnswer(answerId, sessionedUserDto);
        answerDto.update(updatedAnswerDto);
        answerRepository.save(answerDto.toEntity());
    }

    public void delete(Long answerId, UserDto sessionedUserDto) {
        AnswerDto answerDto = getAnswer(answerId, sessionedUserDto);
        answerDto.delete();
        answerRepository.save(answerDto.toEntity());
    }

    public AnswerDto getAnswer(Long answerId, UserDto sessionedUserDto) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.ANSWER_NOT_FOUND));
        AnswerDto answerDto = new AnswerDto(answer);
        if (!answerDto.matchWriter(sessionedUserDto)) {
            throw new IllegalUserAccessException();
        }
        return answerDto;
    }
}
