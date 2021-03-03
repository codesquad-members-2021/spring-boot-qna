package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QnaRepository;
import java.util.List;

public class QnaService {

    QnaRepository qnaRepository;

    public QnaService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    public void save(Question question) {
        qnaRepository.save(question);
    }

    public List<Question> findAll() {
        return qnaRepository.findAll();
    }
}
