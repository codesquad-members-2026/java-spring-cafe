package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UnableToUpdateUserInfo;
import com.codesquad.cafe.exception.UserInfoCannotBeFoundException;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;
    @Mock
    HttpSession httpSession;
    @Mock
    Model model;
    @Mock
    RedirectAttributes redirectAttributes;

    @InjectMocks
    UserController userController;

    @Test
    @DisplayName("Post 방식으로 온전한 회원정보가 들어오면 UserService의 add()을 실행한다.")
    public void join_WithCorrectInfo() {
        User testUser = new User("admin", "admin", "재완", "이",
                "jjjkuul@naver.com", "phoneNumber");

        String requestResult = userController.join(testUser);

        assertEquals("redirect:/user/list", requestResult);
        verify(userService, Mockito.times(1)).addUser(testUser);
    }

    @Test
    @DisplayName("Post 방식으로 잘못된 회원정보가 들어오면 UserService의 add()을 실행하지 않는다.")
    public void join_WithIncorrectInfo() {
        User testUser = new User("admin", "", "재완", "이",
                "jjjkuul@naver.com", "   ");

        String requestResult = userController.join(testUser);

        assertEquals("redirect:/user/join", requestResult);
        verify(userService, never()).addUser(testUser);
    }
    
    @Test
    @DisplayName("로그인을 성공적으로 마치면 홈 화면으로 이동한다.")
    public void login_WithCorrectInfo() {
        when(userService.findUserByIdAndPassword("admin", "admin")).thenReturn(Mockito.mock(User.class));

        assertThat(userController.login("admin", "admin", httpSession)).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("로그인을 실패하면 로그인 창으로 이동한다")
    public void login_WithIncorrectInfo() {
        when(userService.findUserByIdAndPassword("wrongId", "wrongPassword")).thenThrow(UserInfoCannotBeFoundException.class);

        assertThat(userController.login("wrongId", "wrongPassword", httpSession))
                .isEqualTo("redirect:/user/login");
    }

    @Test
    @DisplayName("로그인 상태에 있다면 회원수정창으로 이동할 수 있다.")
    public void modifyForm_WithLoginStatus_CanMove() {
        User testUser = Mockito.mock(User.class);

        assertEquals("user/modify", userController.modifyForm(testUser, model));
        verify(model, Mockito.times(1)).addAttribute("user", testUser);
    }

    @Test
    @DisplayName("로그인 상태에 있지 않다면 회원수정창으로 이동할 수 없다.")
    public void modifyForm_WithLogoutStatus_CantMove() {
        User testUser = null;

        assertEquals("redirect:/", userController.modifyForm(testUser, model));
        verify(model, Mockito.never()).addAttribute("user", testUser);
    }

    @Test
    @DisplayName("회원수정에 성공하면 홈 화면으로 돌아간다.")
    public void update_SuccessModifyUserInfo_RedirectHome(){
        User originUser = Mockito.mock(User.class);
        User updateUser = Mockito.mock(User.class);

        assertEquals("redirect:/",
                userController.update(originUser, httpSession, redirectAttributes, updateUser));

        verify(userService, Mockito.times(1))
                .updateUserInfo(originUser.getId(), updateUser);

        verify(httpSession, Mockito.times(1))
                .setAttribute("sessionUser", updateUser.getLoginId());
    }

    @Test
    @DisplayName("빈 유저 정보가 들어오면 수정 화면으로 돌아간다.")
    public void update_NullUserInfo_RedirectModify(){
        User originUser = null;
        User updateUser = Mockito.mock(User.class);

        assertEquals("redirect:/user/modify",
                userController.update(originUser, httpSession, redirectAttributes, updateUser));
    }

    @Test
    @DisplayName("온전한 객체가 들어왔으나 회원수정 갱신에 실패하면 수정 화면으로 돌아간다.")
    public void update_FailureModifyUserInfo_RedirectModify(){
        User originUser = Mockito.mock(User.class);
        User updateUser = Mockito.mock(User.class);

        when(userService.updateUserInfo(originUser.getId(), updateUser)).thenThrow(UnableToUpdateUserInfo.class);

        String result = userController.update(originUser, httpSession, redirectAttributes, updateUser);

        verify(httpSession, Mockito.never()).setAttribute("sessionUser", updateUser);
        verify(redirectAttributes, Mockito.times(1)).
                addFlashAttribute("errorMessage", "회원 정보 수정에 실패했습니다. 다시 확인해 주세요!");
        assertEquals("redirect:/user/modify", result);
    }
}
