package com.codesquad.cafe.controller;

import com.codesquad.cafe.domain.User;
import com.codesquad.cafe.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(User user) {

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {

        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {

        userRepository.findByUserId(userId).ifPresent(user -> model.addAttribute("user", user));

        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable("userId") String userId, Model model) {

        userRepository.findByUserId(userId).ifPresent(user -> model.addAttribute("user", user));
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable("userId") String userId, User updatedUser) {

        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.getPassword().equals(updatedUser.getPassword())) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                userRepository.save(user); // 덮어쓰기!
                return "redirect:/users";
            } else {
                return "redirect:/users/" + userId + "/form";
            }
        }
        return "redirect:/users";
    }
}