package com.codesquad.cafe.user;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
public class UserControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private HttpSession httpSession;

    @Test
    @DisplayName("유효한 회원가입 폼 데이터를 받으면 회원 리스트 창으로 리다이렉트한다.")
    public void join_HttpPostTest() throws Exception{
        mockMvc.perform(post("/user/join")
                        .param("id", "admin")
                        .param("password", "admin")
                        .param("firstName", "젠슨")
                        .param("lastName", "황")
                        .param("email", "nvidia@gmail.com")
                        .param("phoneNumber", "01049291779"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        Mockito.verify(userService, Mockito.times(1)).add(Mockito.any(User.class));
    }

    @Test
    @DisplayName("유효한 로그인 폼 데이터를 받으면 홈 화면으로 리다이렉트한다.")
    public void login_HttpPostTest() throws Exception{
        when(userService.findUser("admin", "admin")).thenReturn(Mockito.mock(User.class));

        mockMvc.perform(post("/user/login")
                .param("id", "admin")
                .param("password", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Mockito.verify(userService, Mockito.times(1)).findUser("admin", "admin");
    }
}
