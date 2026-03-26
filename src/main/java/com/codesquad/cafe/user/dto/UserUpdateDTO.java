package com.codesquad.cafe.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserUpdateDTO {
    @NotNull(message = "PK가 존재하지 않습니다.")
    private Long id;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "성을 입력해주세요.")
    private String lastName;

    @NotBlank(message = "이름을 입력해주세요.")
    private String firstName;

    @Email(message = "올바른 이메일 형식이 아닙니다..")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    public UserUpdateDTO(Long id, String password, String lastName,
                         String firstName, String email, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {return id;}
    public String getPassword() {
        return password;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
