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
import java.util.Objects;

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
        userId = Objects.toString(userId,""); // null 값일 경우 "" 정의

        for(User findUser:userList){
            if(findUser.getUserId().equals(userId)) {
                model.addAttribute("invalidMember",true);
                model.addAttribute("userID",findUser.getUserId());
                model.addAttribute("email",findUser.getEmail());
            }else{
                model.addAttribute("invalidMember",true);
            }
        }
        return "user/profile";
    }

    @GetMapping("users/{userId}/form")
    private String changeMemberInfo(@PathVariable("userId") String userId, Model model){
        userId = Objects.toString(userId,""); // null 값일 경우 "" 정의

        for(User findUser:userList){
            if(Objects.equals(findUser.getUserId(),userId)) {
                model.addAttribute("invalidMember",false);
                model.addAttribute("userID",findUser.getUserId());
                model.addAttribute("password",findUser.getPassword());
                model.addAttribute("name",findUser.getName());
                model.addAttribute("email",findUser.getEmail());
                break;
            }else{
                model.addAttribute("invalidMember",true);
            }
        }
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    private String updateMemberList(User updateUser, Model model){

        for(int index = 0; index<userList.size() ; index++){
            boolean findIdCheck = Objects.equals(userList.get(index).getUserId(),updateUser.getUserId()); // null 비교가능
            boolean findPasswordCheck = Objects.equals(userList.get(index).getPassword(),updateUser.getPassword()); // null 비교가능

            if (findIdCheck && findPasswordCheck){
                model.addAttribute("invalidPassword",false);
                userList.get(index).setName(Objects.toString(updateUser.getName(),"")); // null default
                userList.get(index).setEmail(Objects.toString(updateUser.getEmail(),"")); // null default
            }else{
                model.addAttribute("invalidPassword",true);
            }
            model.addAttribute("users",userList);
        }
        return "user/list";
    }

}

