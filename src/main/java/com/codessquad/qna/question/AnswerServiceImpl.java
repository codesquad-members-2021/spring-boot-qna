package com.codessquad.qna.question;

import com.codessquad.qna.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    private Answer getAnswer(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
    }

    @Override
    public void createAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public void deleteAnswers(List<Answer> answers) {
        answerRepository.deleteAll(answers);
    }

    @Override
    public void deleteAnswer(Long id, UserDTO currentSessionUser) {
        Answer answer = getAnswer(id);

        if (!answer.getWriter().isIdSameAs(currentSessionUser.getId())) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "",
                    null,
                    null,
                    StandardCharsets.UTF_8
            );
        }

        answerRepository.delete(answer);
    }
}
