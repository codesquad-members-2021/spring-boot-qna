package com.codessquad.qna.service;

import com.codessquad.qna.domain.pagination.Criteria;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public Long create(Question question) {
        return questionRepository.save(question).getId();
    }

    @Transactional
    public Long update(Long id, Question questionWithUpdatedInfo) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        question.update(questionWithUpdatedInfo);

        return id;
    }

    @Transactional
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAllByDeletedIsFalse();
    }

    @Transactional
    public void deleteById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        List<Answer> answers = answerRepository.findAllByQuestionIdAndDeletedIsFalse(id);

        boolean isQuestionAnsweredByOnlyItself = answers.stream()
                .allMatch(question::isAnsweredYourself);
        if (!isQuestionAnsweredByOnlyItself) {
            throw new IllegalArgumentException("다른 사용자의 답변이 있습니다.");
        }
        question.delete();
    }

    public Page<Question> pagingList(Criteria cri) {
        PageRequest pageRequest = PageRequest.of(cri.getPageNum() - 1, cri.getSize(), Sort.by("id").descending());
        Page<Question> questions = questionRepository.findAllByDeletedIsFalse(pageRequest);
        return questions;
    }

    public int notDeletedSize() {
        return questionRepository.findAllByDeletedIsFalse().size();
    }
}
