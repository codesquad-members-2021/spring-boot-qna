package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.Result;
import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        //여기 예외처리 안되는 것 같음
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        return answerService.postAnswer(user, questionId, contents);
    }

    @DeleteMapping("/{id}")
    public Result deleteAnswer(@PathVariable("questionId") long questionId, @PathVariable("id") long id, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다");
        }
        //findById에서 error나면 catch 어떻게 하는지 고민
        // 그리고 위에는 세션 유저가 없을 때 return null 안하고 예외 발생 한느데 여기는 Result.fail 하지 그 차이도 고려해야 해
        User loginUser = HttpSessionUtils.getSessionedUser(session).get();
        //Servcie의 delete메소드 이름 변경해야 겠다.
        if(!answerService.deleteAnswer(id, loginUser)) {
            return Result.fail("자신의 글만 삭제할 수 있습니다");
        }
        return Result.ok();
    }
}
