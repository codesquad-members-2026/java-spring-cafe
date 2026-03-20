package com.codesquad.cafe.home;

import com.codesquad.cafe.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = "sessionUser", required = false) User loginUser, Model model) {
        if(loginUser != null){
            model.addAttribute("user", loginUser);
        }

        return "index";
    }

    @GetMapping("/dev/login")
    public String login(HttpSession session) {
        User devUser = new User("admin", "1234", "리자", "관",
                "jjjkuul@naver.com", "01049291779");

        session.setAttribute("sessionUser", devUser);

        return "redirect:/";
    }
}
