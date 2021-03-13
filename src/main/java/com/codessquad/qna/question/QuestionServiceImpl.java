package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import com.codessquad.qna.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    @Override
    public List<Question> getQuestions() {
        return StreamSupport.stream(questionRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    @Override
    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다. id : " + id));
    }

    @Override
    public Question getQuestion(Long id, UserDTO currentSessionUser) {
        Question question = getQuestion(id);

        verifyWriter(question, currentSessionUser.toEntity());

        return question;
    }

    @Override
    public void createQuestion(Question question, UserDTO currentSessionUser) {
        verifyWriter(question, currentSessionUser.toEntity());

        questionRepository.save(question);
    }

    @Override
    public void updateQuestion(Long id, Question newQuestion, UserDTO currentSessionUser) {
        Question existedQuestion = getQuestion(id);

        verifyWriter(existedQuestion, currentSessionUser.toEntity());

        existedQuestion.update(newQuestion);
        questionRepository.save(existedQuestion);
    }

    @Transactional
    @Override
    public void deleteQuestion(Long id, UserDTO currentSessionUser) {
        Question question = getQuestion(id);

        verifyWriter(question, currentSessionUser.toEntity());

        answerService.deleteAnswers(question.getAnswers());
        questionRepository.delete(question);
    }

    private void verifyWriter(Question existedQuestion, User target) {
        if (!existedQuestion.getWriter().isIdSameAs(target.getId())) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "",
                    null,
                    null,
                    StandardCharsets.UTF_8
            );
        }
    }
}
