package com.codesquad.cafeRepo;

import com.codesquad.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepo {
    private final Map<String, User> emailToUserMap = new HashMap<>();

    public void putUser(User user){
        this.emailToUserMap.put(user.getEmail(), user);
    }

    public User getUser(String email){
        if(this.emailToUserMap.containsKey(email)){
            return this.emailToUserMap.get(email);
        }
        return null;
    }

    public List<User> userList(){
        return this.emailToUserMap.values().stream().toList();
    }

    public void clear(){
        this.emailToUserMap.clear();
    }
}
