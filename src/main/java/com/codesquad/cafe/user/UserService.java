package com.codesquad.cafe.user;

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

    List<User> getUsers(){
        return users;
    }
}
