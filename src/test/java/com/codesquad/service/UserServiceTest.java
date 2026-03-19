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
        softTests.assertThat(testService.findUserById("user1")).isNotNull();
        softTests.assertThat((testService.findUserById("user1")).getId()).isEqualTo("user1");
        softTests.assertThat(testService.findUserById("user1")).isEqualTo(newUser);
        softTests.assertAll();
    }

    @Test
    @DisplayName("Test All users added are returned from list")
    void userListTest(){
        SoftAssertions softTests = new SoftAssertions();
        User newUser = new User();
        newUser.setId("id1");
        testService.addUser(newUser);
        newUser = new User();
        newUser.setId("id2");
        testService.addUser(newUser);
        newUser = new User();
        newUser.setId("id3");
        testService.addUser(newUser);
        newUser = new User();
        newUser.setId("id4");
        testService.addUser(newUser);
        newUser = new User();
        newUser.setId("id5");
        testService.addUser(newUser);
        softTests.assertThat(testService.allUsers().size()).isEqualTo(5);
        softTests.assertThat(testService.allUsers().stream().filter(u->u.getId().equals("id1")).findFirst()).isNotNull();
        softTests.assertThat(testService.allUsers().stream().filter(u->u.getId().equals("id2")).findFirst()).isNotNull();
        softTests.assertThat(testService.allUsers().stream().filter(u->u.getId().equals("id3")).findFirst()).isNotNull();
        softTests.assertThat(testService.allUsers().stream().filter(u->u.getId().equals("id4")).findFirst()).isNotNull();
        softTests.assertThat(testService.allUsers().stream().filter(u->u.getId().equals("id5")).findFirst()).isNotNull();
    }


}
