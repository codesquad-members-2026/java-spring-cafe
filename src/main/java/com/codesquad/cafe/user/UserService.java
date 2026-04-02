package com.codesquad.cafe.user;

import com.codesquad.cafe.user.dto.LoginUser;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    public LoginUser login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("이메일 혹은 비밀번호가 잘못되었습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 혹은 비밀번호가 잘못되었습니다.");
        }

        return new LoginUser(user.getId(), user.getName());
    }

    public void validateOwner(Long loginUserId, Long ownerId) {
        if (!loginUserId.equals(ownerId)) {
            throw new IllegalArgumentException("해당 글을 조회할 권한이 없습니다.");
        }
    }
}
