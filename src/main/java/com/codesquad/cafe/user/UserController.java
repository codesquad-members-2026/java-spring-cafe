package com.codesquad.cafe.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            model.addAttribute("loginUser", session.getAttribute("loginUser"));
        }

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
    public String login(@RequestParam String email,
                        @RequestParam String password, HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        try {
            User user = userService.login(email, password);
            request.getSession().setAttribute("loginUser", user.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/login";
        }

        return "redirect:/users";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/users";
    }

    @GetMapping("/{ownerId}")
    public String getUserById(@PathVariable Long ownerId, Model model, HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            return "redirect:/users/login";
        }

        Long loginUserId = (Long) session.getAttribute("loginUser");
        try {
            userService.validateOwner(loginUserId, ownerId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users";
        }

        User user = userService.get(ownerId);

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "/user/users-detail";
    }
}
