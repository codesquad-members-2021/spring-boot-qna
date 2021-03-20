package com.codessquad.qna.question;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.answer.AnswerService;
import com.codessquad.qna.exception.InsufficientAuthenticationException;
import com.codessquad.qna.exception.ResourceNotFoundException;
import com.codessquad.qna.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    public List<QuestionDTO> readAll() {
        List<QuestionDTO> result = questionRepository.findAllByDeletedFalse().stream()
                .map(QuestionDTO::from)
                .collect(Collectors.toList());

        for (QuestionDTO question : result) {
            question.setAnswers(answerService.readAll(question.getId()));
        }

        return result;
    }

    public QuestionDTO read(Long id) {
        QuestionDTO result = QuestionDTO.from(questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("질문이 존재하지 않습니다. id : " + id)));

        result.setAnswers(answerService.readAll(id));

        return result;
    }

    public QuestionDTO readVerifiedQuestion(Long id, UserDTO user) {
        Question result = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("질문이 존재하지 않습니다. id : " + id));

        result.verifyWriter(user.toEntity());

        return QuestionDTO.from(result);
    }

    public void create(QuestionDTO question) {
        questionRepository.save(question.toEntity());
    }

    public void update(Long id, QuestionDTO newQuestion) {
        Question existedQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("질문이 존재하지 않습니다. id : " + id));

        existedQuestion.update(newQuestion.toEntity());
        questionRepository.save(existedQuestion);
    }

    @Transactional
    public void delete(Long id, UserDTO currentSessionUser) {
        Question question = readVerifiedQuestion(id, currentSessionUser).toEntity();
        List<Answer> answer = question.getAnswers();

        boolean differentWriterExists = answer.stream()
                .anyMatch(question::isWriterDifferentFrom);

        if (differentWriterExists) {
            throw new InsufficientAuthenticationException("다른 작성자가 작성한 답변이 있으면 삭제할 수 없습니다.");
        }

        answerService.deleteAll(question.getAnswers());
        question.delete();
        questionRepository.save(question);
    }
}
