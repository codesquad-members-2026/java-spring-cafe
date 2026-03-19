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

    // 회원가입 창으로 이동
    @GetMapping("/membership")
    public String signupForm(Model model) {

        return "user/form";
    }

    // 회원가입 폼 제출
    @PostMapping("/form")
    public String join(@ModelAttribute User input) {
        // 파일에 저장 -> UserService의 saveUserInFile(user) 실행
        if(input.verifyUser()){
            userService.add(input);
            return "redirect:/list";
        }

        return "redirect:/";
    }

    // 유저 리스트 창으로 이동
    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    // 로그인 창으로 이동
    @GetMapping("/login")
    public String loginForm(Model model) {

        return "user/login";
    }
}
