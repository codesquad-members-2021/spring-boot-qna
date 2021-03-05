package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<User> userList = new ArrayList<>();

    @PostMapping("/user/create")
    private String register(User user){
        userList.add(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    @GetMapping("/users")
    private String getMemberList(Model model){
        model.addAttribute("users",userList);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    private String displayProfile(@PathVariable("userId") String userId, Model model){

        for(User findUser:userList){
            if(findUser.getUserId().equals(userId)) {
                model.addAttribute("userID",findUser.getUserId());
                model.addAttribute("email",findUser.getEmail());
                break;
            }
        }
        return "user/profile";
    }

    @GetMapping("users/{userId}/form")
    private String changeMemberInfo(@PathVariable("userId") String userId, Model model){
        for(User findUser:userList){
            if(findUser.getUserId().equals(userId)) {
                model.addAttribute("userID",findUser.getUserId());
                model.addAttribute("password",findUser.getPassword());
                model.addAttribute("name",findUser.getName());
                model.addAttribute("email",findUser.getEmail());
                break;
            }
        }
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    private String updateMemberList(User updateUser, Model model){

        for(int index = 0; index<userList.size() ; index++){
            boolean findeCheck = userList.get(index).getUserId().equals(updateUser.getUserId());
            if (findeCheck){
                userList.get(index).setName(updateUser.getName());
                userList.get(index).setEmail(updateUser.getEmail());
                userList.get(index).setPassword(updateUser.getPassword());
            }
        }

        return "redirect:/users";
    }

}

