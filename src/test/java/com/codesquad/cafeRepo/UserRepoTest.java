package com.codesquad.cafeRepo;

import com.codesquad.user.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;


public class UserRepoTest {

    private UserRepo repo;

    @BeforeEach
    void setUp(){
        repo = new UserRepo();
    }

    @ParameterizedTest
    @CsvSource({
            "userid1,userid1@test.com",
            "userid2,userid2@test.com" ,
            "userid3,userid3@test.com",
            "userid4,userid4@test.com",
            "userid5,userid5@test.com"
    })
    @DisplayName("Test adding and retrieving users to repository")
    void testPutUser(String id, String email){
        User newUser = new User();
        newUser.setId(id);
        newUser.setEmail(email);
        repo.putUser(newUser);
        assertThat(repo.getUserByEmail(email).getId()).isEqualTo(id);
        assertThat(repo.getUserByEmail(email).getEmail()).isEqualTo(email);
        assertThat(repo.getUserByEmail(email)).isEqualTo(newUser);
    }

}
