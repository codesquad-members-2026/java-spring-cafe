package com.codesquad.cafeRepo;

import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;

import java.util.List;

public interface InterfaceRepo {

    public void putUser(User user);

    public User getUserById(String id);

    public List<User> userList();
}
