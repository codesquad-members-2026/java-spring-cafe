package com.codesquad.cafe.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    UserService userService;
    UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("Post 방식으로 온전한 회원정보가 들어오면 UserService의 add()을 실행한다.")
    public void join_WithCorrectInfo() {
        User testUser = new User("admin", "admin", "재완", "이",
                "jjjkuul@naver.com", "phoneNumber");

        String requestResult = userController.join(testUser);

        assertEquals("redirect:/login", requestResult);
        verify(userService, Mockito.times(1)).add(testUser);
    }

    @Test
    @DisplayName("Post 방식으로 잘못된 회원정보가 들어오면 UserService의 add()을 실행하지 않는다.")
    public void join_WithIncorrectInfo() {
        User testUser = new User("admin", "", "재완", "이",
                "jjjkuul@naver.com", "   ");

        String requestResult = userController.join(testUser);

        assertEquals("redirect:/", requestResult);
        verify(userService, never()).add(testUser);
    }
    
    @Test
    @DisplayName("회원가입되어 있다면 홈 화면으로 이동한다.")
    public void login_WithCorrectInfo() {
        when(userService.isExist("admin", "admin")).thenReturn(true);

        assertThat(userController.login("admin", "admin")).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("회원가입되어 있지 않다면 로그인 창으로 이동한다")
    public void login_WithIncorrectInfo() {
        when(userService.isExist("admin", "admin")).thenReturn(false);

        assertThat(userController.login("admin", "admin")).isEqualTo("redirect:/login");
    }
}
