package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.*;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

import java.util.*;

import static com.codessquad.qna.controller.HttpSessionUtils.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void update(Question question) {
        questionRepository.save(question);
    }

    public void update(Question question, Question updatedQuestion) {
        question.update(updatedQuestion);
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Page<Question> findPage(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question findVerifiedQuestion(Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        checkPermission(session, question);
        return question;
    }

    private void checkPermission(HttpSession session, Question question) {
        if (!isLoginUser(session)) {
            throw new IllegalUserAccessException("로그인이 필요합니다.");
        }
        User loginUser = getSessionUser(session);
        if (question.isNotSameAuthor(loginUser)) {
            throw new IllegalUserAccessException();
        }
    }

    private class PageNumber {
        private int pageId;
        private boolean active;
        private int pageNumber;

        private PageNumber(int pageId, boolean active, int pageNumber) {
            this.pageId = pageId;
            this.active = active;
            this.pageNumber = pageNumber;
        }

        public int getPageId() {
            return pageId;
        }

        public boolean isActive() {
            return active;
        }

        public int getPageNumber() {
            return pageNumber;
        }
    }

    public void addPages(Model model, Pageable pageable) {
        Page<Question> page = findPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("hasPrevious", page.hasPrevious());
        model.addAttribute("hasNext", page.hasNext());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        List<PageNumber> pages = new ArrayList<>();
        for (int pageId = 0; pageId < page.getTotalPages(); pageId++) {
            if (Math.abs(pageId - pageable.getPageNumber()) <= 2) {
                boolean active = false;
                if (pageable.getPageNumber() == pageId) {
                    active = true;
                }
                pages.add(new PageNumber(pageId, active, pageId + 1));
                while (pages.size() > 5) {
                    pages.remove(0);
                }
            } else if (pages.size() < 5) {
                pages.add(new PageNumber(pageId, false, pageId + 1));
            }
        }
        model.addAttribute("pages", pages);
    }
}

