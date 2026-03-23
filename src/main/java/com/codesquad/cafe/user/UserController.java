package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.NoUserInListException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 폼 제출
    @PostMapping("/join")
    public String join(@ModelAttribute User unregisUser) {
        if(unregisUser.verifyUser()){
            userService.add(unregisUser);
            return "redirect:/user/list";
        }

        return "redirect:/user/join";
    }

    // 유저 리스트 창으로 이동
    @GetMapping("/list")
    public void listForm(Model model) {
        var users = userService.getUsers();
        model.addAttribute("users", users);
    }
    
    // 유저 프로필 창으로 이동
    @GetMapping("/{id}")
    public String profileForm(@PathVariable("id") String id, Model model){
        try {
            User user = userService.findUser(id);
            model.addAttribute("user", user);
            return "/user/profile";
        } catch (NoUserInListException e) {
            return "redirect:/user/list";
        }
    }

    // 로그인 폼 제출
    @PostMapping("/login")
    public String login(String id, String password, HttpSession session) {
        try {
            User user = userService.findLoginUser(id, password);
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        } catch(NoUserInListException e) {
            return "redirect:/user/login";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");

        return "redirect:/";
    }

    // 회원정보수정 창으로 이동
    @GetMapping("/modify")
    public String modifyForm(
            @SessionAttribute(name = "sessionUser", required = false) User loginUser, Model model) {
        if(loginUser != null){
            model.addAttribute("user", loginUser);
            return "user/modify";
        }

        return "redirect:/";
    }
    // 수정된 회원 정보 폼 제출
    @PostMapping("/update")
    public String update(
            @SessionAttribute(name = "sessionUser", required = false) User loginUser,
            HttpSession session, @ModelAttribute User modifiedUser) {

        if(loginUser.updateUser(modifiedUser)){
            session.setAttribute("sessionUser", loginUser);
            return "redirect:/";
        }

        return "redirect:/user/modify";
    }
}
