package com.codesquad.cafeController;

import org.junit.jupiter.api.*;
import com.codesquad.user.User;
import com.codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("GET /user/signup should return signup html within template ")
    void getSignupPageTest() throws Exception{
        mockMvc.perform(get("/user/signup")).andExpect(status().isOk()).andExpect(view().name("signup"));
    }

    @Test
    @DisplayName("POST /user/signup should add new user to repo and redirect to /user/")
    void postSignupPageTest() throws Exception{
        mockMvc.perform(post("/user/signup")
                .param("id","testid")
                .param("email","email@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/users"))
                .andExpect(redirectedUrl("/user/users"));

        verify(userService).addUser(any(User.class));
    }

    @Test
    @DisplayName("GET /users should return the list of users in the model")
    void getUsersListTest() throws Exception{
        User dummyUser1 = new User();
        dummyUser1.setEmail("dummy1@test.com");
        dummyUser1.setId("dummy1");
        User dummyUser2 = new User();
        dummyUser2.setEmail("dummy2@test.com");
        dummyUser2.setId("dummy2");
        List<User> fakeList = new ArrayList<>(Arrays.asList(dummyUser1, dummyUser2));
        when(userService.allUsers()).thenReturn(fakeList);
        mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",fakeList));
    }


}
