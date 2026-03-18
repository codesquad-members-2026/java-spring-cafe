package com.codesquad.cafe;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public void save(User user) {
        users.add(user);
    }

    public List<User> getAll() {
        return users;
    }
}
