package com.codesquad.cafeController;

import com.codesquad.service.UserService;
import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{id}")
    public String getUserDetail(@PathVariable String id, Model model){
        model.addAttribute("user", service.findUserById(id));
        return "user/userDetail";
    }

    @GetMapping("/users/{id}/edit")
    public String getUserInfoModPage(@PathVariable String id, Model model){
        model.addAttribute("user",service.findUserById(id));
        return "user/editProfile";
    }


    @PutMapping("/users/{id}/edit")
    public String putUserInfoMod(@PathVariable String id, UserUpdateForm form, Model model, RedirectAttributes redirectAttrs){
        User existingUser = service.findUserById(id);
        if(service.updateUserProfile(existingUser.getId(), form)){
            model.addAttribute("user", existingUser);
            return "user/userDetail";
        }
        else{
            redirectAttrs.addFlashAttribute("errorMessage", "Incorrect Credentials. Please try again");
            return "redirect:/user/users/{id}/edit";
        }
    }
}
