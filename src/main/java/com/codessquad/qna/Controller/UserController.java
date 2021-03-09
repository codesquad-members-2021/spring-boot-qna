package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import org.graalvm.compiler.lir.LIRInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    final private List<User> userList = new ArrayList<>();

    @GetMapping("users/form.html")
    public String form() {
        System.out.println(" form :: in");
        return "users/form";
    }

    @PostMapping("/users/create")
    public String create(User user) {
        userList.add(user);

        return "redirect:/userslist";
    }



    @GetMapping("/userslist")
    public String list(Model model) {
        System.out.println("userslist");
        model.addAttribute("userlist",userList);

        return "users/list";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable(name="userId") String userId, Model model) {
        System.out.println("updateForm@@@");
        User currentUser;
        for(User user : userList) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user",user);
                break;
            }
        }

        return "users/updateForm";
    }



    @PostMapping("/users/{userId}")
    public String update(@PathVariable String userId, Model model) {
        User currentUser = getUserByUserId(userId);
        model.addAttribute("user",currentUser);
        logger.info("update User : " + currentUser.toString());

        //업데이트를 하고 나서는 이동해야되니까 리다이렉트가 필요함
        return "redirect:/userslist";
    }


    public User getUserByUserId(String userId) {
        for(User user: userList) {
            if(user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
