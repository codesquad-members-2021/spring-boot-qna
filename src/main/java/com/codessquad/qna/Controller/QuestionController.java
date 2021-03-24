package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.utils.SessionUtil.isLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isValidUser;

@Controller

@RequestMapping("/qna")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionRepostory questionRepostory;

    private final AnswerRepository answerRepository;

    public QuestionController(QuestionRepostory questionRepostory, AnswerRepository answerRepository) {
        this.questionRepostory = questionRepostory;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/")
    public String qnaPage() {
        return "redirect:/qna/list";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session, Model model) {

        if (!isLoginUser(session)) {
            return "redirect:/user/login";
        }
        logger.info("questionForm");
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question, HttpSession session) {
        Question addNewQuestion = new Question(question, session);
        questionRepostory.save(addNewQuestion);
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String showQuestionList(Model model) {
        model.addAttribute("questionList", questionRepostory.findAll());
        return "/qna/list";
    }

    @GetMapping("/{id}")
    public String showDetailQuestion(@PathVariable Long id, Model model) throws Exception {
        Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        List<Answer> answerList = answerRepository.findByQuestionId(id);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("answerList", answerList);
        logger.info("update Question : {}" + currentQuestion.getTitle());
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) throws Exception {
        Question question = questionRepostory.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        question.update(title, contents);
        questionRepostory.save(question);
        logger.info("update Question : {id}", id);
        return String.format("redirect:/qna/%d", id);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, User ownerUser, HttpSession session) {

        Question question = questionRepostory.findById(id).orElseThrow(NotFoundException::new);

        if (!isValidUser(session, question.getWriter())) {
            logger.info("질문글 삭제 - 실패, 권한없는 사용자의 삭제시도");
            return String.format("redirect:/qna/%d", id);
        }
        questionRepostory.delete(questionRepostory.getOne(id));
        logger.info("질문글 삭제 - 성공");
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

        Question question = questionRepostory.findById(id).orElseThrow(NotFoundException::new);
        if (!isValidUser(session, question.getWriter())) {
            logger.info("질문글 수정 - 실패, 권한없는 사용자의 수정시도");
            return String.format("redirect:/qna/%d", id);
        }
        logger.info("글 수정 : {}", question.getTitle());
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
}
