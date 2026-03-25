package com.codesquad.cafe.user;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void addUser(User user){
        userRepository.add(user);
    }

    int countUser(){
        return userRepository.count();
    }

    List<User> getUsers(){
        return userRepository.getUsers();
    }

    User findLoginUser(String id, String password){
        return userRepository.matchIdPassword(id, password);
    }

    User findUser(String id){
        return userRepository.matchId(id);
    }
}
