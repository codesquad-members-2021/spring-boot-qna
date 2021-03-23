package com.codessquad.qna.Controller;

import static com.codessquad.qna.utils.SessionUtil.*;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepostory;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;

@Controller

@RequestMapping("/qna")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final QuestionRepostory questionRepostory;


    public QuestionController(QuestionRepostory questionRepostory) {
        this.questionRepostory = questionRepostory;
    }


    @GetMapping("/form")
    public String questionForm(HttpSession session,Model model) {

        if(!isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User user = getLoginUser(session);
        logger.info("askQuestion");
        model.addAttribute("user",user);
        return "qna/form";
    }

    @PostMapping("/qna/questions")
    public String createQuestion(Question question, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        question.setWriter(sessionUser);
        questionRepostory.save(question);
        return "redirect:/qna/list";
    }

    @GetMapping("/qna/list")
    public String showQuestionList(Model model) {
        model.addAttribute("questionList", questionRepostory.findAll());
        return "qna/list";
    }

    @GetMapping("/qna/{index}")
    public String showProfile(@PathVariable Long id, Model model) throws Exception {
        Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        model.addAttribute("question", currentQuestion);
        logger.info("update Question : " + currentQuestion.toString());
        return "/qna/show";
    }
}
