package com.codesquad.cafe.controller;

import com.codesquad.cafe.domain.User;
import com.codesquad.cafe.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다: " + userId));

        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable("userId") String userId, Model model,
                             HttpSession session, RedirectAttributes rttr) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");


        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }


        if (!sessionedUser.getUserId().equals(userId)) {
            rttr.addFlashAttribute("errorMessage", "자신의 정보만 수정할 수 있습니다.");
            return "redirect:/";
        }

        model.addAttribute("user", sessionedUser);
        return "user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String update(@PathVariable("userId") String userId, User updatedUser, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null || !sessionedUser.getUserId().equals(userId)) {
            return "redirect:/";
        }

        if (!sessionedUser.getPassword().equals(updatedUser.getPassword())) {
            return "redirect:/users/" + userId + "/form";
        }

        sessionedUser.setName(updatedUser.getName());
        sessionedUser.setEmail(updatedUser.getEmail());
        userRepository.save(sessionedUser);

        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginProcess(String userId, String password, HttpSession session, RedirectAttributes rttr) {
        Optional<User> loginUser = userRepository.findByUserId(userId);

        if (loginUser.isPresent()) {
            User user = loginUser.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("sessionedUser", user);
                return "redirect:/";
            } else {
                rttr.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
                return "redirect:/users/loginForm";
            }
        }

        rttr.addFlashAttribute("errorMessage", "존재하지 않는 사용자입니다.");
        return "redirect:/users/loginForm";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}