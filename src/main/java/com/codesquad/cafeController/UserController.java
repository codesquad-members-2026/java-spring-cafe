package com.codesquad.cafeController;

import com.codesquad.cafeRepo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    UserRepo repo = new UserRepo();

    @GetMapping("/users")
    public String getUsersList(Model model){
        model.addAttribute(repo.userList());
        return "/users";
    }
}
