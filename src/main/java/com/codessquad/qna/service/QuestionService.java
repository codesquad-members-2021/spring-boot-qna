package com.codessquad.qna.service;

import com.codessquad.qna.util.HttpSessionUtils;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public QuestionService(AnswerService answerService, QuestionRepository questionRepository) {
        this.answerService = answerService;
        this.questionRepository = questionRepository;
    }

    public void create(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = new Question(loginUser, title, contents);
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void update(Long id, String title, String contents) {
        Question question = questionRepository.findById(id).orElseThrow(NotFoundException::new);
        question.updateQuestion(title, contents);
        logger.info("question {}. ", question);
        questionRepository.save(question);
    }

    public void delete(Long questionId, User loginUser) {
        List<Answer> activeAnswers = answerService.findAnswers(questionId);
        Question question = findById(questionId);
        if (question.canDelete(loginUser, activeAnswers)) {
            for (Answer answer : activeAnswers) {
                answer.delete();
            }
            question.delete();
            questionRepository.save(question);
        }
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Page<Question> findAllQuestion() {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        return this.questionRepository.findAllByIsDeleteFalse(pageable);
    }

    public void searchPage(Model model, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        Page<Question> questions = questionRepository.findAllByIsDeleteFalse(PageRequest.of(page, 15, Sort.Direction.DESC, "id"));
        int currentPage = questions.getNumber() + 1;
        int totalPages = questions.getTotalPages();
        model.addAttribute("questions", questions);
        model.addAttribute("pageUtil", new PageUtil(currentPage, totalPages));
    }

}
