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

import static com.codessquad.qna.utils.SessionUtil.*;

@Controller

@RequestMapping("/qna")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final QuestionRepostory questionRepostory;

    public QuestionController(QuestionRepostory questionRepostory) {
        this.questionRepostory = questionRepostory;
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
        Question addNewQuestion = new Question(question,session);
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
        model.addAttribute("question", currentQuestion);
        logger.info("update Question : {}" + currentQuestion.getTitle());
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

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id,User ownerUser,HttpSession session) {

        Question question =  questionRepostory.findById(id).get();// 옵셔녈

        if(! isValidUser(session,question.getWriter())) {
            logger.info("질문글 삭제 - 실패, 권한없는 사용자의 삭제시도");
            return String.format("redirect:/qna/%d",id);
        }
        questionRepostory.delete(questionRepostory.getOne(id));
        logger.info("질문글 삭제 - 성공");
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model,HttpSession session) {

        Question question =  questionRepostory.findById(id).get();//옵셔녈 겟 수정
        if(! isValidUser(session,question.getWriter())) {
            logger.info("질문글 수정 - 실패, 권한없는 사용자의 수정시도");
            return String.format("redirect:/qna/%d",id);
        }
        logger.info("글 수정 : {}",question.getTitle());
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
}
