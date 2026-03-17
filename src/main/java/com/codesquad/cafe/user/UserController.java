package com.codesquad.cafe.user;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/form")
    public String requestMembership(@ModelAttribute User input) {
        // 파일에 저장 -> UserService의 saveUserInFile(user) 실행
        if(input.verifyUser()){
            userService.add(input);
        }

        return "redirect:/users";
    }
}
