package com.codesquad.cafe;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public void save(User user) {
        user.setId(users.size() + 1);
        users.add(user);
    }

    public List<User> getAll() {
        return users;
    }

    public User get(int id) {
        return users.get(id - 1);
    }
}
