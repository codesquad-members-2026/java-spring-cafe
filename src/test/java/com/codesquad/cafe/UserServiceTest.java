package com.codesquad.cafe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService();
    }

    @Test
    @DisplayName("회원을 추가하고 조회할 수 있다.")
    public void addAndGet() throws Exception {
        //given
        User user1 = new User();
        user1.setName("gabi");
        user1.setEmail("gabi@example.com");
        user1.setPassword("gabigabi");

        User user2 = new User();
        user1.setName("gabi2");
        user1.setEmail("gab2i@example.com");
        user1.setPassword("gabi2gabi2");

        userService.save(user1);
        userService.save(user2);

        //when
        List<User> users = userService.getAll();

        //then
        assertThat(users).containsExactlyInAnyOrderElementsOf(List.of(user1, user2));
    }
}