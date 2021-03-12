package com.codessquad.qna.web;

import com.codessquad.qna.domain.Qna;
import com.codessquad.qna.domain.QnaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;

@Controller
public class QuestionController {
    private final QnaRepository qnaRepository;

    @Inject
    public QuestionController(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @PostMapping("/qna")
    public String createNewQna(Qna qna) {
        if (qna == null) {
            return "redirect:/qna";
        }
        qnaRepository.save(qna);
        return "redirect:/";
    }

    @GetMapping("/")
    public String qnaList(Model model) {
        model.addAttribute("qnaList", qnaRepository.findAll());
        return "index";
    }

    @GetMapping("qna/{qnaId}")
    public String showOneQuestion(@PathVariable long qnaId, Model model) {
        model.addAttribute("Qna", qnaRepository.findById(qnaId).orElseThrow(NullPointerException::new));
        return "qna/show";
    }
}
