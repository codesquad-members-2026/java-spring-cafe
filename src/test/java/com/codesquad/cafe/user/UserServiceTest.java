package com.codesquad.cafe.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {
    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("User 객체를 리스트에 저장한다")
    public void add_OneUserAddList_ReturnOne(){
        User user = new User("admin", "admin", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");

        userService.add(user);

        assertEquals(1, userService.count());
    }
}
