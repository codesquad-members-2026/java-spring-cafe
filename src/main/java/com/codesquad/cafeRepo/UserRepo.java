package com.codesquad.cafeRepo;

import com.codesquad.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepo {
    private Map<String, User> idToUserMap = new HashMap<>();

    public void putUser(User user){
        this.idToUserMap.put(user.getId(), user);
    }

    public User getUser(String id){
        if(this.idToUserMap.containsKey(id)){
            return this.idToUserMap.get(id);
        }
        return null;
    }

    public List<User> userList(){
        return this.idToUserMap.values().stream().toList();
    }
}
