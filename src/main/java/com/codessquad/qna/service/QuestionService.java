package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.dto.QuestionDto;
import com.codessquad.qna.model.dto.UserDto;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(QuestionDto questionDto, UserDto sessionedUserDto) {
        questionDto.save(sessionedUserDto);
        questionRepository.save(questionDto.toEntity());
    }

    public void update(Long questionId, QuestionDto newQuestionDto, UserDto sessionedUserDto) {
        QuestionDto oldQuestionDto = verifyQuestion(questionId, sessionedUserDto);
        oldQuestionDto.update(newQuestionDto);
        questionRepository.save(oldQuestionDto.toEntity());
    }

    public boolean delete(Long questionId, UserDto sessionedUserDto) {
        QuestionDto questionDto = verifyQuestion(questionId, sessionedUserDto);
        boolean result = questionDto.delete();
        if (result) {
            questionRepository.save(questionDto.toEntity());
        }
        return result;
    }

    public QuestionDto findById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        return new QuestionDto(question);
    }

    public List<QuestionDto> findAll() {
        return questionRepository.findAllByDeletedFalse().stream()
                .map(QuestionDto::new)
                .collect(Collectors.toList());
    }

    public QuestionDto verifyQuestion(Long id, UserDto sessionedUserDto) {
        QuestionDto questionDto = findById(id);
        if (!questionDto.matchWriter(sessionedUserDto)) {
            throw new IllegalUserAccessException();
        }
        return questionDto;
    }
}
