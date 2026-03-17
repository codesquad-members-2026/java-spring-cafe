package com.codesquad.cafeController;

import com.codesquad.cafeRepo.UserRepo;
import com.codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    UserService service;

    @GetMapping("/users")
    public String getUsersList(Model model){

        return "/users";
    }
}
