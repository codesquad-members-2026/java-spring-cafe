package com.codesquad.cafeController;

import com.codesquad.service.UserService;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService userService){
        this.service = userService;
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute User user){
        service.addUser(user);
        return "redirect:/user/users";
    }

    @GetMapping("/users")
    public String getUsersList(Model model){
        model.addAttribute("users", service.allUsers());
        return "user/users";
    }
}
