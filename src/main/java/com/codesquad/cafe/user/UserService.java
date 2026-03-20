package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.NoUserInListException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users;

    UserService(){
        users = new ArrayList<>();
    }

    void add(User user) {
        users.add(user);
    }

    int count(){
        return users.size();
    }

    User findUser(String id, String password){
        for(User user : users){
            if(user.getId().equals(id) && user.getPassword().equals(password)){
                return user;
            }
        }

        throw new NoUserInListException("해당 유저가 존재하지 않습니다.");
    }

    User findUser(String id){
        for(User user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }

        throw new NoUserInListException("해당 유저가 존재하지 않습니다.");
    }

    List<User> getUsers(){
        return users;
    }
}
