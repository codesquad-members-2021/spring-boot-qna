package com.codessquad.qna.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// FIXME: MvcConfig 에서 index 의 리다이렉션을 유도할 수는 없을까?
@Controller
public class IndexController {
    @GetMapping("/")
    public String index_redirect() {
        return "redirect:/questions";
    }
}
