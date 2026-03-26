package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UnableToUpdateUserInfo;
import com.codesquad.cafe.exception.UserInfoCannotBeFoundException;
import com.codesquad.cafe.user.dto.UserUpdateDTO;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @Mock
    BindingResult bindingResult;

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
        when(userService.findUserByIdAndPassword("wrongId", "wrongPassword"))
                .thenThrow(UserInfoCannotBeFoundException.class);

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
        UserUpdateDTO updateDto = new UserUpdateDTO(1L, "1234a",
                "이", "재완", "jjjkuul@naver.com", "01049291779");
        User updateUser = new User("wodhks7727", "123456abcdef",
                "황", "젠슨", "nvidia@naver.com", "01058492212");

        ReflectionTestUtils.setField(updateUser, "id", 1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.updateUserInfo(updateDto.getId(), updateDto)).thenReturn(updateUser);

        assertEquals("redirect:/",
                userController.update(updateDto, bindingResult, httpSession, redirectAttributes));
        verify(userService, Mockito.times(1))
                .updateUserInfo(updateDto.getId(), updateDto);
        verify(httpSession, Mockito.times(1))
                .setAttribute("sessionUser", updateUser);
    }

    @Test
    @DisplayName("빈 유저 정보가 들어오면 수정 화면으로 돌아간다.")
    public void update_NullUserInfo_RedirectModify(){
        UserUpdateDTO updateDto = Mockito.mock(UserUpdateDTO.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertEquals("redirect:/user/modify",
                userController.update(updateDto, bindingResult, httpSession, redirectAttributes));
    }

    @Test
    @DisplayName("온전한 객체가 들어왔으나 회원수정 갱신에 실패하면 수정 화면으로 돌아간다.")
    public void update_FailureModifyUserInfo_RedirectModify(){
        UserUpdateDTO updateDto = new UserUpdateDTO(1L, "1234a",
                "이", "재완", "jjjkuul@naver.com", "01049291779");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.updateUserInfo(updateDto.getId(), updateDto)).thenThrow(UnableToUpdateUserInfo.class);

        String result = userController.update(updateDto, bindingResult, httpSession, redirectAttributes);

        verify(httpSession, Mockito.never()).setAttribute(eq("sessionUser"), any(User.class));
        verify(redirectAttributes, Mockito.times(1)).
                addFlashAttribute("errorMessage", "회원 정보 수정에 실패했습니다. 다시 확인해 주세요!");
        assertEquals("redirect:/user/modify", result);
    }
}
