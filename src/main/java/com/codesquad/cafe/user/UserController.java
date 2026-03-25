package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UnableToUpdateUserInfo;
import com.codesquad.cafe.exception.UserInfoCannotBeFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
        TODO
        - DTO 만들어서 1차 데이터 검증 로직 작성
        -> User가 직접 데이터를 검증하도록 만들면..
        1. 화면의 단순 입력값 검증 로직과 핵심 비즈니스 로직이 한 곳에 섞여 코드가 너무 비대해지고 유지 보수에 어려움이 생김
        2. 브라우저에서 날아온 데이터가 DB와 직결된 엔티티에 필터링 없이 꽂히게 되어 악의적인 데이터 조작(Overposting 등) 보안 사고의 위험 존재
        3. 로그인할 때(아이디/비번만 필요)와 회원 가입할 때(모든 정보 필요)처럼 상황마다 다른 검증 규칙을 엔티티 하나로 감당할 수 없음
     */
    @PostMapping("/join")
    public String join(@ModelAttribute User unregisUser) {
        if(unregisUser.verifySignup()){
            userService.addUser(unregisUser);
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
    @GetMapping("/{loginId}")
    public String profileForm(@PathVariable("loginId") String loginId, Model model){
        try {
            User user = userService.findUserById(loginId);
            model.addAttribute("user", user);
            return "/user/profile";
        } catch (UserInfoCannotBeFoundException e) {
            return "redirect:/user/list";
        }
    }

    // 로그인 페이지 이동
    @GetMapping("/login")
    public void loginForm() {}
    // 로그인 폼 제출
    @PostMapping("/login")
    public String login(String loginId, String password, HttpSession session) {
        try {
            User user = userService.findUserByIdAndPassword(loginId, password);
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        } catch(UserInfoCannotBeFoundException e) {
            return "redirect:/user/login";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

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
    
    // TODO: 직접 폼 데이터를 브라우저에게 받지 말고 DTO를 만들어서 포장된 데이터로 전달받기
    // 수정된 회원 정보 폼 제출
    @PutMapping("/update")
    public String update(
            @SessionAttribute(name = "sessionUser", required = false) User loginUser,
            HttpSession session, RedirectAttributes redirectAttributes,
            @ModelAttribute User modifiedUser) {

        if(loginUser != null){
            Long id = loginUser.getId();

            try {
                User updateUser = userService.updateUserInfo(id, modifiedUser);
                session.setAttribute("sessionUser", updateUser);
                return "redirect:/";
            } catch (UnableToUpdateUserInfo e) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "회원 정보 수정에 실패했습니다. 다시 확인해 주세요!");
                return "redirect:/user/modify";
            } catch(UserInfoCannotBeFoundException e) {
                return "redirect:/";
            }
        }

        return "redirect:/user/modify";
    }
}
