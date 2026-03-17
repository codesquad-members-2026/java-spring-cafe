package com.codesquad.service;
import com.codesquad.cafeRepo.UserRepo;
import com.codesquad.user.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    UserRepo testRepo = new UserRepo();
    UserService testService = new UserService(testRepo);

    @BeforeEach
    void setUp(){
        testRepo.clear();
    }

    @Test
    @DisplayName("New User being added successfully")
    void addUserTest(){
        SoftAssertions softTests = new SoftAssertions();

        User newUser = new User();
        newUser.setId("user1");
        testService.addUser(newUser);
        softTests.assertThat(testService.findUser("user1")).isNotNull();
        softTests.assertThat((testService.findUser("user1")).getId()).isEqualTo("user1");
        softTests.assertThat(testService.findUser("user1")).isEqualTo(newUser);
        softTests.assertAll();
    }

    @ParameterizedTest
    @CsvSource({
            "user1",
            "user1",
            "user1",
            "user1"
    })
    @DisplayName("Test All users added are returned from list")
    void userListTest()


}
