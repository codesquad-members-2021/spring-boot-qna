package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepostory;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isLoginUser;

@Controller

@RequestMapping("/qna")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final QuestionRepostory questionRepostory;

    public QuestionController(QuestionRepostory questionRepostory) {
        this.questionRepostory = questionRepostory;
    }

    @GetMapping("/")
    public String qnapage() {
        return "redirect:/qna/list";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session, Model model) {

        if (!isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User user = getLoginUser(session);
        logger.info("askQuestion");
        model.addAttribute("user", user);
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        question.setWriter(sessionUser);
        questionRepostory.save(question);
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
        model.addAttribute("question", currentQuestion);
        logger.info("update Question : " + currentQuestion.toString());
        return "/qna/show";
    }

    //질문수정
    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) throws Exception {
        Question question = questionRepostory.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        question.update(title,contents);
        questionRepostory.save(question);
        logger.info("update Question : {id}",id);
        return String.format("redirect:/qna/%d",id);
    }


    //수정필요
    @PostMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, Model model) throws Exception {
        Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        model.addAttribute("question", currentQuestion);
        logger.info("update Question : " + currentQuestion.toString());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        Question question =  questionRepostory.findById(id).get();
        logger.info(question.toString());
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
}
