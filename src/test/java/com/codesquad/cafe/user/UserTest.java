package com.codesquad.cafe.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

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

        assertEquals(true, user1.verifyUser());
        assertEquals(false, user2.verifyUser());
        assertEquals(false, user3.verifyUser());
        assertEquals(false, user4.verifyUser());
    }


    // TODO: 정규표현식을 적용하여 추후 리팩토링
}
