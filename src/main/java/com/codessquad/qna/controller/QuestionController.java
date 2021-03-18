package com.codessquad.qna.controller;

import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
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
        HttpSessionUtils.checkValidOf(session);
        question.setWriter(HttpSessionUtils.getLoginUserOf(session)); // 이부분은 나중에 없앨 수 있을 거 같다.
        questionService.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}") // 메인화면에서 질문리스트 중 하나를 클릭할 경우
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        Question questionData = questionService.findById(targetId); // findById() 내부에서 인자 예외처리
        model.addAttribute("answer", questionData.getAnswers()); // 질문에 대응되는 답글만 가져온다.
        model.addAttribute("question", questionData);
        model.addAttribute("answerCount", questionData.getAnswers().size()); // 답글 수
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form") // 질문글에서 수정버튼을 눌렀을 때에 해당하는 맵핑
    private String modifyQuestionButton(@PathVariable("id") Long id, Model model, HttpSession session) {
        HttpSessionUtils.checkValidOf(session);
        questionService.confirmWriter(HttpSessionUtils.getLoginUserOf(session), questionService.findById(id));
        model.addAttribute("question", questionService.findById(id));
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")  // 질문 수정: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String modifyQuestion(@PathVariable("id") Long id, String title, String contents) {
        Question findQuestion = questionService.findById(id);
        findQuestion.setTitle(title); // 인자 내부 예외처리, 이부분 서비스나 Question 객체안에서 해야할까..
        findQuestion.setContents(contents);
        questionService.save(findQuestion);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}") // 질문 삭제: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String deleteQuestion(@PathVariable Long id, HttpSession session, Model model) {
        HttpSessionUtils.checkValidOf(session);
        questionService.confirmWriter(HttpSessionUtils.getLoginUserOf(session), questionService.findById(id));
        questionService.deleteById(id); // soft delete로 변경해야함.
        return "redirect:/";
    }
}
