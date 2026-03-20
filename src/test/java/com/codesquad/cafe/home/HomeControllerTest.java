package com.codesquad.cafe.home;

import com.codesquad.cafe.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    @DisplayName("URL '/'로 매핑했을때 로그인 정보가 세션에 남아있다면 model에 User 객체를 추가하고 index를 반환한다.")
    public void home_addUserOnModel_ReturnIndex(){
        User testUser = Mockito.mock(User.class);

        assertEquals("index", homeController.home(testUser, model));
        verify(model, Mockito.times(1)).addAttribute("user", testUser);
    }

    @Test
    @DisplayName("URL '/'로 매핑했을때 로그인 정보가 세션에 남아있지 않다면 model에 User 객체를 추가하지 않고 index를 반환한다.")
    public void home_NotAddUserOnModel_ReturnIndex(){
        User testUser = null;

        assertEquals("index", homeController.home(testUser, model));
        verify(model, Mockito.never()).addAttribute("user", testUser);
    }
}
