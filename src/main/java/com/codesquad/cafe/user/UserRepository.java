package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.NoUserInListException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    void add(User user) {
        users.add(user);
    }

    int count(){
        return users.size();
    }

    User matchIdPassword(String id, String password) {
        String errMsg = "No user found with id " + id + " and password " + password;

        return findUserByCondition(user -> user.isLoginMatch(id, password), errMsg);
    }
    User matchId(String id) {
        String errMsg = "No user found with id " + id;

        return findUserByCondition(user -> user.isIdMatch(id), errMsg);
    }
    private User findUserByCondition(Predicate<User> condition, String errorMessage){
        for(User user : users){
            if(condition.test(user)){
                return user;
            }
        }
        throw new NoUserInListException(errorMessage);
    }

    List<User> getUsers(){
        return users;
    }
}
