package com.codesquad.cafe.user;

import com.codesquad.cafe.user.dto.LoginUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);

        model.addAttribute("users", userService.getAll());
        return "/user/users";
    }

    @PostMapping("")
    public String signup(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "/user/login-form";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session) {
        session.setAttribute("loginUser",  userService.login(email, password));

        return "redirect:/users";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/users";
    }

    @GetMapping("/{ownerId}")
    public String getUserById(@PathVariable Long ownerId, Model model,
                              @SessionAttribute(name = "loginUser", required = false) LoginUser loginUser) {
        userService.validateOwner(loginUser.getId(), ownerId);

        User user = userService.get(ownerId);

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "/user/users-detail";
    }
}
