package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/qna/form") // 메인 메뉴에서 질문하기 버튼을 누를 경우
    private String questionButton(HttpSession session) {
        HttpSessionUtils.checkValidOf(session);
        return "qna/form";
    }

    @PostMapping("/questions") // 게시글 작성하고 질문하기 버튼을 누를 경우
    private String createQuestions(Question question, HttpSession session) {
        questionService.save(question, session);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}") // 메인화면에서 질문리스트 중 하나를 클릭할 경우
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        Question questionData = questionService.findById(targetId);
        model.addAttribute("answer", questionData.getAnswers()); // 질문에 대응되는 답글만 가져온다.
        model.addAttribute("question", questionData);
        model.addAttribute("answerCount", questionData.getAnswers().size()); // 답글 수
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form") // 질문글에서 수정버튼을 눌렀을 때에 해당하는 맵핑
    private String modifyQuestionButton(@PathVariable("id") Long questionId, Model model, HttpSession session) {
        questionService.authenticateOfId(questionId,session);
        model.addAttribute("question", questionService.findById(questionId));
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")  // 질문 수정: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String modifyQuestion(@PathVariable("id") Long questionId, String title, String contents, HttpSession session) {
        questionService.update(questionId, session, title, contents);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}") // 질문 삭제: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String deleteQuestion(@PathVariable("id") Long questionId, HttpSession session) {
        questionService.delete(questionId,session);
        return "redirect:/";
    }
}
