package com.codesquad.cafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/form")
    public String requestMembership(User input) {
        // 파일에 저장 -> UserService의 saveUserInFile(user) 실행
        if(input.verifyUser()){
            userService.add(input);
        }

        return "redirect:/";
    }
}
