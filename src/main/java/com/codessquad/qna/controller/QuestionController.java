package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.validation.CastValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    List<Question> questions = new ArrayList<>();

    @GetMapping("/form")
    public String getForm(){
        return "qna/form";
    }

    @GetMapping("/{index}")
    public String getQuestionDetail(@PathVariable("index") String stringIdx, Model model){
        int index = getIndex(stringIdx);
        Question question = questions.get(index - 1);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @PostMapping("/")
    public String create(Question question){
        question.setId(nextId());
        questions.add(question);
        return "redirect:/";
    }

    public int getIndex(String stringIdx){
        int index = CastValidation.stringToInt(stringIdx);
        if(index < 0 || index > questions.size()){
            throw new IndexOutOfBoundsException(stringIdx + " is out of range!");
        }
        return index;
    }

    public int nextId(){
        return questions.size() + 1;
    }

}
