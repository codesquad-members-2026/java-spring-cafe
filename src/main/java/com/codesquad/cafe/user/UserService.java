package com.codesquad.cafe.user;

import com.codesquad.cafe.global.exception.LoginFailedException;
import com.codesquad.cafe.global.exception.NotOwnerException;
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
                .orElseThrow(LoginFailedException::new);

        if (!user.getPassword().equals(password)) {
            throw new LoginFailedException();
        }

        return new LoginUser(user.getId(), user.getName());
    }

    public void validateOwner(Long loginUserId, Long ownerId) {
        if (!loginUserId.equals(ownerId)) {
            throw new NotOwnerException();
        }
    }
}
