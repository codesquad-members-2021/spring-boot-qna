package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.model.Question;
import com.codessquad.qna.web.validation.CastValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    List<Question> questions = new ArrayList<>();

    @GetMapping("/questions/form")
    public String getForm(){
        return "qna/form";
    }

    @GetMapping("/questions/{index}")
    public String getQuestionDetail(@PathVariable("index") String stringIdx, Model model){
        int index = getIndex(stringIdx);
        Question question = questions.get(index);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @PostMapping("/questions")
    public String create(Question question){
        question.setId(nextId());
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("questions", questions);
        return "index";
    }

    public int getIndex(String stringIdx){
        System.out.println(questions.size());
        int index = CastValidation.stringToInt(stringIdx) - 1;
        if(index < 0 || index > questions.size()){
            throw new IndexOutOfBoundsException(stringIdx + " is out of range!");
        }
        return index;
    }

    public int nextId(){
        return questions.size() + 1;
    }

}
