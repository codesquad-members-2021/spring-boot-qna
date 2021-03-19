package com.codessquad.qna.question;

import com.codessquad.qna.answer.AnswerService;
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
        return questionRepository.findAll();
    }

    public Question read(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다. id : " + id));
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

        answerService.delete(question.getAnswers());
        questionRepository.delete(question);
    }
}
