package com.codesquad.cafe.controller;

import com.codesquad.cafe.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final List<User> users = new ArrayList<>();


    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/profile";
    }


}
