package com.codesquad.cafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            return "redirect:/users";
        }

        return "redirect:/";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        return "user/users";
    }
}
