package com.codessquad.qna.answer;

import com.codessquad.qna.exception.ResourceNotFoundException;
import com.codessquad.qna.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<AnswerDTO> readAll(Long questionId) {
        return answerRepository.findAllByQuestionIdAndDeletedFalse(questionId).stream()
                .map(AnswerDTO::from)
                .collect(Collectors.toList());
    }

    private Answer readExistedAnswer(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 답변입니다. id : " + id));
    }

    public AnswerDTO readVerifiedAnswer(Long id, UserDTO user) {
        Answer answer = readExistedAnswer(id);

        answer.verifyWriter(user.toEntity());

        return AnswerDTO.from(answer);
    }

    public AnswerDTO create(AnswerDTO answer) {
        Answer savedAnswer = answerRepository.save(answer.toEntity());

        int answersCount = countBy(savedAnswer.getQuestion().getId());
        AnswerDTO result = AnswerDTO.of(savedAnswer, answersCount);

        return result;
    }

    public void update(Long id, AnswerDTO newAnswer) {
        Answer existedAnswer = readExistedAnswer(id);

        existedAnswer.update(newAnswer.toEntity());
        answerRepository.save(existedAnswer);
    }

    public void deleteAll(List<Answer> answers) {
        for (Answer answer : answers) {
            answer.delete();
        }

        answerRepository.saveAll(answers);
    }

    public AnswerDTO delete(Long id, UserDTO currentSessionUser) {
        Answer answer = readExistedAnswer(id);

        answer.verifyWriter(currentSessionUser.toEntity());
        answer.delete();
        Answer deletedAnswer = answerRepository.save(answer);

        int answersCount = countBy(deletedAnswer.getQuestion().getId());
        return AnswerDTO.of(answerRepository.save(answer), answersCount);
    }

    private int countBy(Long questionId) {
        return answerRepository.countByQuestionIdAndDeletedFalse(questionId);
    }
}
