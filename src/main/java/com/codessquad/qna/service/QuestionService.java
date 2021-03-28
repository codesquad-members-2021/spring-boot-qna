package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.utils.SessionUtil.isLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isValidUser;

@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepostory questionRepostory;

    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepostory questionRepostory, AnswerRepository answerRepository) {
        this.questionRepostory = questionRepostory;
        this.answerRepository = answerRepository;
    }

    /*
    public void isLogin(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new LoginFailedException("");
        }
        logger.info("questionForm");
    }*/

    public void createQuestion(Question question, User user) {
        Question addNewQuestion = new Question(question, user);
        questionRepostory.save(addNewQuestion);
        //@Todo 질문을 만드는데 질문이 필요하다..?
        //@Todo 비어있는 타이틀이나, 비어있는 컨텐츠가 들어오면 그냥 저장되는 문제..
    }

    public Question showDetailQuestion(Long id) {
        return questionRepostory.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Question> findAll() {
        return questionRepostory.findAllByDeletedFalse();
    }

    public void updateQuestion(Long id, String title, String contents) {
        Question question = questionRepostory.findById(id).orElseThrow(NotFoundException::new);
        question.update(title, contents);
        questionRepostory.save(question);
        logger.info("update Question : {}", id);
    }

    public void deleteQuestion(Long questionId, HttpSession session) {
        Question question = questionRepostory.findById(questionId).orElseThrow(NotFoundException::new);

        if (!isValidUser(session, question.getWriter())) {
            logger.info("질문글 삭제 - 실패, 권한없는 사용자의 삭제시도");
            throw new UnauthorizedException("질문글 삭제 - 실패, 권한없는 사용자의 삭제시도");
        }

        if (!question.isDeleteable()) {
            throw new UnauthorizedException("질문글 삭제 - 실패, 다른사람의 답변이 달린 질문글은 삭제할 수 없습니다");
        }
        question.deleteQuestion();
        questionRepostory.save(question);
        logger.info("질문글 삭제 - 성공");

    }

    public void updateForm(Long id, Model model, HttpSession session) {
        Question question = questionRepostory.findById(id).orElseThrow(NotFoundException::new);
        if (!isValidUser(session, question.getWriter())) {
            logger.info("질문글 수정 - 실패, 권한없는 사용자의 수정시도");
            throw new UnauthorizedException("질문글 수정 - 실패, 권한없는 사용자의 수정시도");
        }
        logger.info("글 수정 : {}", question.getTitle());
        model.addAttribute("question", question);
    }


}
