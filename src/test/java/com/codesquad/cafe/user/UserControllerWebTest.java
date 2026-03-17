package com.codesquad.cafe.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
public class UserControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Post 요청으로 들어온 회원가입 폼 데이터를 받아 리다이렉트한다.")
    public void requestMembership_HttpPostTest() throws Exception{
        mockMvc.perform(post("/form")
                        .param("id", "admin")
                        .param("password", "admin")
                        .param("firstName", "젠슨")
                        .param("lastName", "황")
                        .param("email", "nvidia@gmail.com")
                        .param("phoneNumber", "01049291779"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Mockito.verify(userService, Mockito.times(1)).add(Mockito.any(User.class));
    }
}
