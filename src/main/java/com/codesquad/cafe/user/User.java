package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UnableToUpdateUserInfo;
import com.codesquad.cafe.user.dto.UserUpdateDTO;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    protected User(){};

    public User(String loginId, String password, String lastName,
                String firstName, String email, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // TODO: 다음 커밋에서 지워질 내역 -> DTO로 역할 넘기기
    public boolean verifySignup() {
        return !(loginId.isEmpty() || loginId.isBlank())
                && !(password.isEmpty() || password.isBlank())
                && !(lastName.isEmpty() || lastName.isBlank())
                && !(firstName.isEmpty() || firstName.isBlank())
                && !(email.isEmpty() || email.isBlank())
                && !(phoneNumber.isEmpty() || phoneNumber.isBlank());
    }

    // User 정보 수정
    public User updateUser(UserUpdateDTO updateDTO) {
        if(!checkPassword(updateDTO.getPassword()) || !checkBlank(updateDTO))
            throw new UnableToUpdateUserInfo("Failed to update user information");

        setField(updateDTO);
        return this;
    }
    private boolean checkPassword(String password) {
        return password.equals(this.password);
    }
    private boolean checkBlank(UserUpdateDTO modifiedUser) {
        return !modifiedUser.getEmail().contains(" ") && !modifiedUser.getFirstName().contains(" ")
                && !modifiedUser.getLastName().contains(" ") && !modifiedUser.getPhoneNumber().contains(" ");
    }
    private void setField(UserUpdateDTO modifiedUser) {
        this.firstName = modifiedUser.getFirstName();
        this.lastName = modifiedUser.getLastName();
        this.email = modifiedUser.getEmail();
        this.phoneNumber = modifiedUser.getPhoneNumber();
    }

    // getter, setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLoginId() {
        return loginId;
    }
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(loginId, user.loginId) && Objects.equals(password, user.password) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, loginId, password, lastName, firstName, email, phoneNumber);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
