package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UserInfoCannotBeFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("User 객체를 리스트에 저장한다")
    public void testAdd(){
        User user = new User("admin", "admin", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");

        userService.addUser(user);

        assertEquals(1L, userService.countUsers());
    }

    @Test
    @DisplayName("인자로 들어온 아이디와 패스워드 값과 일치하는 정보를 가진 User가 users에 존재한다.")
    public void isExist_ReturnTrue(){
        User user = new User("admin", "admin", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");
        userService.addUser(user);

        assertEquals(userService.findUserByIdAndPassword("admin", "admin"), user);
    }

    @Test
    @DisplayName("인자로 들어온 아이디와 패스워드 값과 일치하는 정보를 가진 User가 users에 존재하지 않는다.")
    public void isExist_ReturnFalse(){
        User user = new User("admin", "adminnn", "젠슨", "황",
                "nvidia@gmail.com", "01049291779");
        userService.addUser(user);

        assertThrows(UserInfoCannotBeFoundException.class, () -> userService.findUserByIdAndPassword("admin", "admin"));
    }
}
