package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtil;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/qna/form") // 메인 메뉴에서 질문하기 버튼을 누를 경우
    private String questionButton(Model model, HttpSession session) {
        if (HttpSessionUtil.checkValidOf(session)) {
            return "qna/form";
        }
        // 비로그인 사용자가 질문하기 버튼에 접근할 경우
        model.addAttribute("errorMessage", "로그인 후 질문기능을 사용할 수 있습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }

    @PostMapping("/questions") // 게시글 작성하고 질문하기 버튼을 누를 경우
    private String createQuestions(Question question, Model model, HttpSession session) {

        if (HttpSessionUtil.checkValidOf(session)) {
            question.setWriter(HttpSessionUtil.getLoginUserOf(session)); // 이부분은 나중에 없앨 수 있을 거 같다.
            questionService.save(question);
            return "redirect:/";
        }
        model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }

    @GetMapping("/questions/{id}") // 메인화면에서 질문리스트 중 하나를 클릭할 경우
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        Objects.requireNonNull(targetId, "Exception: targetKey가 NULL 값입니다.");

        Question questionData = questionService.findById(targetId);
        model.addAttribute("answer", questionData.getAnswers()); // 질문에 대응되는 답글만 가져온다.
        model.addAttribute("question", questionData);
        model.addAttribute("answerCount", questionData.getAnswers().size()); // 답글 수
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form") // 질문글에서 수정버튼을 눌렀을 때에 해당하는 맵핑
    private String modifyQuestionButton(@PathVariable("id") Long id, Model model, HttpSession session) {

        if (HttpSessionUtil.checkValidOf(session)) {
            if(questionService.confirmWriter(HttpSessionUtil.getLoginUserOf(session),questionService.findById(id))){
                model.addAttribute("question",questionService.findById(id));
                return "qna/updateForm";
            }
        }

        model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }


    @PutMapping("/questions/{id}")  // 질문 수정: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String modifyQuestion(@PathVariable("id") Long id, String title, String contents) {

        Question findQuestion = questionService.findById(id);
        findQuestion.setTitle(title);
        findQuestion.setContents(contents);
        questionService.save(findQuestion);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}") // 질문 삭제: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String deleteQuestion(@PathVariable Long id, HttpSession session, Model model){

        if (HttpSessionUtil.checkValidOf(session)) {
            if(questionService.confirmWriter(HttpSessionUtil.getLoginUserOf(session),questionService.findById(id))){
                questionService.deleteById(id);
                return "redirect:/";
            }
        }
        model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }
}
