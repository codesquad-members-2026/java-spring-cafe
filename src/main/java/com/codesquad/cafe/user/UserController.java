package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.NoUserInListException;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 창으로 이동
    @GetMapping("/signup")
    public String signupForm(Model model) {

        return "user/signup";
    }
    // 회원가입 폼 제출
    @PostMapping("/join")
    public String join(@ModelAttribute User input) {
        // 파일에 저장 -> UserService의 saveUserInFile(user) 실행
        if(input.verifyUser()){
            userService.add(input);
            return "redirect:/login";
        }

        return "redirect:/";
    }

    // 유저 리스트 창으로 이동
    @GetMapping("/list")
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
    // 로그인 폼 제출
    @PostMapping("/login")
    public String login(String id, String password, HttpSession session) {
        try {
            User user = userService.findUser(id, password);
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        } catch(NoUserInListException e) {
            // TODO: 이후 로그로 작성 필요
            return "redirect:/login";
        }
    }
}
