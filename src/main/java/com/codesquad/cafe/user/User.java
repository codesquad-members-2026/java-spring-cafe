package com.codesquad.cafe.user;

import java.util.Objects;

public class User {
    private String id;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    public User(String id, String password, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // 유저 정보 검증
    public boolean verifySignup() {
        return !(getId().isEmpty() || getId().isBlank())
                && !(getPassword().isEmpty() || getPassword().isBlank())
                && !(getFirstName().isEmpty() || getFirstName().isBlank())
                && !(getLastName().isEmpty() || getLastName().isBlank())
                && !(getEmail().isEmpty() || getEmail().isBlank())
                && !(getPhoneNumber().isEmpty() || getPhoneNumber().isBlank());
    }
    public boolean isLoginMatch(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }
    public boolean isIdMatch(String id) {
        return this.id.equals(id);
    }

    // User 정보 수정
    public boolean updateUser(User modifiedUser) {
        if(!checkModifiedUser(modifiedUser))
            return false;

        setField(modifiedUser);
        return true;
    }
    private boolean checkModifiedUser(User modifiedUser) {
        return !modifiedUser.getPassword().contains(" ") && !modifiedUser.getEmail().contains(" ") &&
                !modifiedUser.getFirstName().contains(" ") && !modifiedUser.getLastName().contains(" ")
                && !modifiedUser.getPhoneNumber().contains(" ");
    }
    private void setField(User modifiedUser) {
        this.password = isEmpty(modifiedUser.getPassword()) ? password : modifiedUser.getPassword();
        this.firstName = isEmpty(modifiedUser.getFirstName()) ? firstName : modifiedUser.getFirstName();
        this.lastName = isEmpty(modifiedUser.getLastName()) ? lastName : modifiedUser.getLastName();
        this.email = isEmpty(modifiedUser.getEmail()) ? email : modifiedUser.getEmail();
        this.phoneNumber = isEmpty(modifiedUser.getPhoneNumber()) ? phoneNumber : modifiedUser.getPhoneNumber();
    }
    private boolean isEmpty(String string) {
        return string.isEmpty();
    }


    // getter, setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password)
                && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName)
                && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, lastName, firstName, email, phoneNumber);
    }
}
