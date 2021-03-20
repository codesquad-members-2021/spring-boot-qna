package com.codessquad.qna.answer;

import com.codessquad.qna.exception.ResourceNotFoundException;
import com.codessquad.qna.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> readAll(Long questionId) {
        return answerRepository.findAllByQuestionIdAndDeletedFalse(questionId);
    }

    private Answer read(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 답변입니다. id : " + id));
    }

    public Answer readVerifiedAnswer(Long id, UserDTO user) {
        Answer answer = read(id);

        answer.verifyWriter(user.toEntity());

        return answer;
    }

    public Answer create(Answer answer) {
        return answerRepository.save(answer);
    }

    public void update(Long id, Answer newAnswer) {
        Answer existedAnswer = read(id);

        existedAnswer.update(newAnswer);
        answerRepository.save(existedAnswer);
    }

    public void deleteAll(List<Answer> answers) {
        for (Answer answer : answers) {
            answer.delete();
        }

        answerRepository.saveAll(answers);
    }

    public void delete(Long id, UserDTO currentSessionUser) {
        Answer answer = readVerifiedAnswer(id, currentSessionUser);
        answer.delete();

        answerRepository.save(answer);
    }
}
