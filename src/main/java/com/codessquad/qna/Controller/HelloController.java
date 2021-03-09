package com.codessquad.qna.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hihosilver(String name, int age, Model model) {
        model.addAttribute("name",name);
        model.addAttribute("age",age);
        //http://localhost:8080/hello?name=dsf&age=20
        return "welcome";
    }

    @GetMapping("/")
    public String welcomepage() {

        return "index";
    }

}
