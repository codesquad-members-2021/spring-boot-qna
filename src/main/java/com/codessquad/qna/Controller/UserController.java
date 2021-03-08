package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> userlist = new ArrayList<>();

    @GetMapping("user/form.html")
    public String form() {
        System.out.println(" form :: in");
        return "users/form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        userlist.add(user);

        return "redirect:/userslist";
    }



    @GetMapping("/userslist")
    public String list(Model model) {
        System.out.println("userslist");
        model.addAttribute("userlist",userlist);

        return "users/list";
    }

    @GetMapping("/users/{userId}/form")
    public String updateUserForm(@PathVariable(name="userId") String targetId, User reference) {
        //User PresentUser = userlist.get();
        return "users/list";
    }


}
