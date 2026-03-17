package com.codesquad.cafe.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserControllerTest {

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
    }

    @Test
    @DisplayName("Post 방식으로 들어온 회원 정보가 유효하면 saveUserInFile()을 실행한다.")
    public void requestMembership_WithCorrectInfo() {
        User testUser = new User("admin", "admin", "재완", "이",
                "jjjkuul@naver.com", "phoneNumber");
        UserController userController = new UserController(userService);

        userController.requestMembership(testUser);

        verify(userService, Mockito.times(1)).add(testUser);
    }

    @Test
    @DisplayName("Post 방식으로 들어온 잘못된 회원 정보는 saveUserInFile()을 실행하지 않는다.")
    public void requestMembership_WithIncorrectInfo() {
        User testUser = new User("admin", "", "재완", "이",
                "jjjkuul@naver.com", "   ");
        UserController userController = new UserController(userService);

        userController.requestMembership(testUser);

        verify(userService, never()).add(testUser);
    }
}
