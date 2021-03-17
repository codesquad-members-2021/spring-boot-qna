package com.codessquad.qna.web;

import com.codessquad.qna.domain.Qna;
import com.codessquad.qna.domain.QnaRepository;
import com.codessquad.qna.exception.NoQuestionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    private final QnaRepository qnaRepository;

    public QuestionController(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @PostMapping
    public String createNewQna(Qna qna) {
        if (checkEmpty(qna)) {
            return "qna/form";
        }
        qnaRepository.save(qna);
        return "redirect:/";
    }

    private boolean checkEmpty(Qna qna) {
        return qna.getWriter().equals("")
                || qna.getTitle().equals("")
                || qna.getContents().equals("");
    }

    @GetMapping
    public String qnaList(Model model) {
        model.addAttribute("qnaList", qnaRepository.findAll());
        return "index";
    }

    @GetMapping("/{qnaId}")
    public String showOneQuestion(@PathVariable long qnaId, Model model) {
        model.addAttribute("Qna", qnaRepository.findById(qnaId).orElseThrow(NoQuestionException::new));
        return "qna/show";
    }
}
