package com.codessquad.qna.controller.question;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String question_create(Question question) {
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String question_list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "question/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String question_profile(@PathVariable("id") Long id, Model model) {
        // TODO: 조회에 실패했을 경우에 대한 예외 처리 필요; 나중에 findById 로 orElseThrow 하자
        Question question = questionRepository.getOne(id);
        model.addAttribute("question", question);
        return "question/show";
    }
}
