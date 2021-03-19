package com.codessquad.qna.question;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.answer.AnswerService;
import com.codessquad.qna.exception.InsufficientAuthenticationException;
import com.codessquad.qna.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    public List<Question> readAll() {
        List<Question> result = questionRepository.findAllByDeletedFalse();

        for (Question question : result) {
            question.setAnswers(answerService.readAll(question.getId()));
        }

        return result;
    }

    public Question read(Long id) {
        Question result = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다. id : " + id));

        result.setAnswers(answerService.readAll(id));

        return result;
    }

    public Question readVerifiedQuestion(Long id, UserDTO user) {
        Question result = read(id);

        result.verifyWriter(user.toEntity());

        return result;
    }

    public void create(Question question) {
        questionRepository.save(question);
    }

    public void update(Long id, Question newQuestion) {
        Question existedQuestion = read(id);

        existedQuestion.update(newQuestion);
        questionRepository.save(existedQuestion);
    }

    @Transactional
    public void delete(Long id, UserDTO currentSessionUser) {
        Question question = readVerifiedQuestion(id, currentSessionUser);
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
