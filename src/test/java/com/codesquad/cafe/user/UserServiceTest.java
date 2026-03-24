package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.NoUserInListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("User 객체를 리스트에 저장한다")
    public void testAdd(){
        User user = new User("admin", "admin", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");

        userService.addUser(user);

        assertEquals(1, userService.countUser());
    }

    @Test
    @DisplayName("인자로 들어온 아이디와 패스워드 값과 일치하는 정보를 가진 User가 users에 존재한다.")
    public void isExist_ReturnTrue(){
        User user = new User("admin", "admin", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");
        userService.addUser(user);

        assertEquals(userService.findLoginUser("admin", "admin"), user);
    }

    @Test
    @DisplayName("인자로 들어온 아이디와 패스워드 값과 일치하는 정보를 가진 User가 users에 존재하지 않는다.")
    public void isExist_ReturnFalse(){
        User user = new User("admin", "adminnn", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");
        userService.addUser(user);

        assertThrows(NoUserInListException.class, () -> userService.findLoginUser("admin", "admin"));
    }
}
