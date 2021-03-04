package com.codessquad.qna.controller;

import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindUserException;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * UserDto 로 받아와서 User 객체로 Mapping 해준뒤 해당 유저를 생성합니다.
     * @param userDto
     * @return redirect User list (/users)
     */
    @PostMapping("/user")
    public String createAccount(@ModelAttribute UserDto userDto) {
        User user = Mapper.mapToUser(userDto);
        userService.save(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    /**
     * @param model
     * @return All User Accounts to List
     */
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    /**
     * 해당 유저의 프로파일로 이동합니다.
     * 만약 해당 유저를 찾을 수 없다면 CanNotFindUserException 을 리턴합니다.
     * @param id
     * @param model
     * @throws CanNotFindUserException
     * @return only for users with the same id
     */
    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        getUsetIfExist(id, model);
        return "user/profile";
    }

    /**
     * 유저 프로필 수정이 가능한 창으로 이동합니다.
     * 만약 해당 유저를 찾을 수 없다면 CanNotFindUserException 을 리턴합니다.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/users/{id}/form")
    public String updateUserProfileForm(@PathVariable Long id, Model model) {
        getUsetIfExist(id, model);
        return "user/updateForm";
    }

    /**
     * 유저 프로필을 해당 userDto로 온 값으로 업데이트 합니다.
     * @param id
     * @param userDto
     * @return
     */
    @PutMapping("/user/{id}")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        User changeUser = Mapper.mapToUser(userDto);
        userService.change(userService.getUser(id), changeUser);
        return "redirect:/users";
    }

    private void getUsetIfExist(Long id, Model model) {
        try {
            User user = userService.getUser(id);
            model.addAttribute("user", user);
        } catch (CanNotFindUserException e) {
            logger.error(e.getMessage());
        }
    }

}
