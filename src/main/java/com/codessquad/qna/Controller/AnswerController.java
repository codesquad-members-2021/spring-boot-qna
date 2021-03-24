package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isLoginUser;

@Controller
@RequestMapping("/questions/{questionId}/answers") //대표 url을 이렇게 설정한 이유는 >> 앤서는 퀘스천에 종속적이기 때문
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository; //@@안티패턴

    @PostMapping("/qnd/{{id}}/answer")
    public String createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if(!isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User loginUser = getLoginUser(session);
        Answer answer = new Answer(loginUser,contents);
        answerRepository.save(answer);
        return String.format("redirect:/qna/%d",questionId);
    }



}
