package com.codesquad.service;

import com.codesquad.cafeRepo.UserRepo;
import com.codesquad.user.User;

import java.util.List;

public class UserService {

    private final UserRepo repo;

    public UserService(UserRepo repo){
        this.repo = repo;
    }

    public void addUser(User newUser){
        this.repo.putUser(newUser);
    }

    public User findUser(String id){
        return this.repo.getUser(id);
    }


    public List<User> allUsers(){
        return this.repo.userList();
    }
}
