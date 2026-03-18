package com.codesquad.service;

import com.codesquad.cafeRepo.UserRepo;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

    private final UserRepo repo;

    @Autowired
    public UserService(UserRepo repo){
        this.repo = repo;
    }

    public void addUser(User newUser){
        this.repo.putUser(newUser);
    }

    public User findUser(String email){
        return this.repo.getUser(email);
    }


    public List<User> allUsers(){
        return this.repo.userList();
    }
}
