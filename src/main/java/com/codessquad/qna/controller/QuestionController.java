package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/qna/form") // 메인 메뉴에서 질문하기 버튼을 누를 경우
    private String questionButton(Model model, HttpSession session) {
        final Object sessionValue = session.getAttribute("sessionedUser");
        if (sessionValue != null) {
            return "qna/form";
        }
        // 비로그인 사용자가 질문하기 버튼에 접근할 경우
        model.addAttribute("errorMessage", "로그인 후 질문기능을 사용할 수 있습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }

    @PostMapping("/questions") // 게시글 작성하고 질문하기 버튼을 누를 경우
    private String createQuestions(Question question, Model model, HttpSession session) {
        Objects.requireNonNull(question, "Exception: question이 NULL 값입니다.");
        final Object sessionValue = session.getAttribute("sessionedUser");
        if (sessionValue != null) {
            User user = (User) sessionValue;
            question.setWriter(user);
            questionRepository.save(question);
            return "redirect:/";
        }
        model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }

    @GetMapping("/questions/{id}") // 메인화면에서 질문리스트 중 하나를 클릭할 경우
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        Objects.requireNonNull(targetId, "Exception: targetKey가 NULL 값입니다.");

        Optional<Question> questionOptional = questionRepository.findById(targetId);
        Question questionData = questionOptional.orElseThrow(NoSuchElementException::new);

        //model.addAttribute("answer", answerRepository.findByQuestion(questionData)); // 댓글
        model.addAttribute("answer", answerRepository.findByQuestionId(questionData.getId())); // 댓글
        model.addAttribute("question", questionData);

        return "qna/show";
    }

    @GetMapping("/questions/{id}/form") // 질문글에서 수정버튼을 눌렀을 때에 해당하는 맵핑
    private String modifyQuestionButton(@PathVariable("id") Long id, Model model, HttpSession session) {
        Objects.requireNonNull(id, "Exception: id가 NULL 값입니다.");

        Object sessionUser = session.getAttribute("sessionedUser");

        if (sessionUser != null) {
            User q = (User)sessionUser;
            Question q2 = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);

            if(Objects.equals(q.getName(),q2.getWriter())){
                model.addAttribute("question",q2);
                return "qna/updateForm";
            }
        }
        model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }


    @PutMapping("/questions/{id}")  // 질문 수정: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String modifyQuestion(@PathVariable("id") Long id, String title, String contents) {

        Question findQuestion = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        findQuestion.setTitle(title);
        findQuestion.setContents(contents);
        questionRepository.save(findQuestion);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}") // 질문 삭제: 접근경로가 다른 메서드와 동일해도, 맵핑이 다르면 함수의 오버로드 같이 요청에 따라 다르게 인식하는건가?
    private String deleteQuestion(@PathVariable Long id, HttpSession session, Model model){

        Objects.requireNonNull(id, "Exception: id가 NULL 값입니다.");

        Object sessionUser = session.getAttribute("sessionedUser");

        if (sessionUser != null) {
            User q = (User)sessionUser;
            Question q2 = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);

            if(Objects.equals(q.getName(),q2.getWriter())){
                questionRepository.deleteById(id);
                return "redirect:/";
            }
        }
        model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다.");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }
}
