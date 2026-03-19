package com.codesquad.service;

import com.codesquad.cafeRepo.UserRepo;
import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;
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

    public User findUserById(String id){
        return this.repo.getUserById(id);
    }

    public User findUserByEmail(String email){
        return this.repo.getUserByEmail(email);
    }

    public List<User> allUsers(){
        return this.repo.userList();
    }

    public void updateUserProfile(User user, UserUpdateForm form){
        repo.updateUserProfile(user,form);
    }

    public boolean validateFormWithUser(User user, UserUpdateForm form){
        return repo.validateFormWithUser(user,form);
    }
}
