package com.codesquad.cafeRepo;

import com.codesquad.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepo {
    private final Map<String, User> emailToUserMap = new HashMap<>();
    private final Map<String, User> idToUserMap = new HashMap<>();

    public void putUser(User user){
        this.emailToUserMap.put(user.getEmail(), user);
        this.idToUserMap.put(user.getId(), user);
    }

    public User getUserByEmail(String email){
        if(this.emailToUserMap.containsKey(email)){
            return this.emailToUserMap.get(email);
        }
        return null;
    }

    public User getUserById(String id){
        if(this.idToUserMap.containsKey(id)){
            return this.idToUserMap.get(id);
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
