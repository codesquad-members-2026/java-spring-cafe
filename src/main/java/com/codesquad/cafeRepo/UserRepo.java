package com.codesquad.cafeRepo;

import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepo implements InterfaceRepo {
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

    public boolean validateFormWithUser(String userId, UserUpdateForm form){
        User user = this.idToUserMap.get(userId);
        return user.getPassword().equals(form.getPassword());
    }

    public void updateUserProfile(String userId, UserUpdateForm form){
        User user = this.idToUserMap.get(userId);
        if(user == null){
            return;
        }

        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setPassword(form.getNewPassword());

    }

    public List<User> userList(){
        return this.emailToUserMap.values().stream().toList();
    }

    public void clear(){
        this.emailToUserMap.clear();
    }
}
