package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UnableToUpdateUserInfo;
import com.codesquad.cafe.user.dto.UserUpdateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    // TODO: 나중에 정규표현식으로 각 항목 필터링
    @Test
    @DisplayName("모든 필드의 값은 비어있거나 빈칸으로만 구성되어 있으면 안 된다.")
    public void verifyUser_WithCorrectInfo() {
        User user1 = new User("admin", "xhCmjIIHA3%&YN*b", "젠슨", "황",
                "nvida@gmail.com", "01049291779");
        User user2 = new User("admin", "", "젠슨", "황",
                "nvida@gmail.com", "01049291779");
        User user3 = new User("  ", "xhCmjIIHA3%&YN*b", "젠슨", "황",
                "nvida@gmail.com", "01049291779");
        User user4 = new User("", "xhCmjIIHA3%&YN*b", "젠슨", "황",
                "   ", "01049291779");

        assertEquals(true, user1.verifySignup());
        assertEquals(false, user2.verifySignup());
        assertEquals(false, user3.verifySignup());
        assertEquals(false, user4.verifySignup());
    }

    @Test
    @DisplayName("각 요소에 공백, 빈칸이 하나라도 없다면 유저의 정보를 성공적으로 수정한다.")
    public void updateUser_SuccessUpdate(){
        User origin = new User("admin", "xhCmjIIHA3%&YN*b", "젠슨", "황",
                "nvida@gmail.com", "01049291779");
        UserUpdateDTO correctUpdate =
                new UserUpdateDTO(1L, "xhCmjIIHA3%&YN*b", "재완", "이",
                "jjjjkuul@gmail.com", "01049291779");
        User expectedUser = new User("admin", "xhCmjIIHA3%&YN*b", "재완", "이",
                "jjjjkuul@gmail.com", "01049291779");

        ReflectionTestUtils.setField(origin, "id", 1L);
        ReflectionTestUtils.setField(expectedUser, "id", 1L);
        origin.updateUser(correctUpdate);

        assertEquals(expectedUser, origin);
    }

    @Test
    @DisplayName("각 요소에 공백이 하나라도 있다면 유저 정보 업데이트를 취소한다.")
    public void updateUser_FailureUpdate(){
        User origin = new User("admin", "xhCmjIIHA3%&YN*b", "젠슨", "황",
                "nvida@gmail.com", "01049291779");
        UserUpdateDTO incorrectUpdate = new UserUpdateDTO(1L, " A  ", "비", "가",
                "jjjkuul@naver.com", "01054849166");
        User expectedUser = new User("admin", "xhCmjIIHA3%&YN*b", "젠슨", "황",
                "nvida@gmail.com", "01049291779");

        ReflectionTestUtils.setField(origin, "id", 1L);
        ReflectionTestUtils.setField(expectedUser, "id", 1L);

        assertThrows(UnableToUpdateUserInfo.class, () -> origin.updateUser(incorrectUpdate));
        assertEquals(expectedUser, origin);
    }
}
