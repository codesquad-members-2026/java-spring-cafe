package com.codesquad.service;

import com.codesquad.cafeRepo.InterfaceRepo;
import com.codesquad.cafeRepo.JpaUserRepo;
import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserService {

    private final JpaUserRepo repo;

    @Autowired
    public UserService(JpaUserRepo repo){
        this.repo = repo;
    }

    public void addUser(User newUser){
        this.repo.save(newUser);
    }

    public User findUserById(String id){
        return this.repo.getUserById(id);
    }

    public List<User> allUsers(){
        return this.repo.findAll();
    }

    public boolean updateUserProfile(String userId, UserUpdateForm form){
        User user = this.repo.getUserById(userId);
        if(user.getPassword().equals(form.getPassword())){
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            user.setPassword(form.getNewPassword());
            this.repo.save(user);
            return true;
        }
        return false;
    }




}
