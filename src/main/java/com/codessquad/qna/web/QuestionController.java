package com.codessquad.qna.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {

    List<Qna> qnaList = new ArrayList<>();

    @PostMapping("/qna")
    public String qnaMain(String writer, String title, String contents) {
        Qna newQna = new Qna(qnaList.size()+ 1, writer,title,contents);
        System.out.println("QnA: "+writer );
        qnaList.add(newQna);
        return "redirect:/";
    }

}
