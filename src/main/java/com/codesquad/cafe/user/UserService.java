package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UserInfoCannotBeFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository  jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Transactional
    public void addUser(User user){
        jpaUserRepository.save(user);
    }

    public Long countUsers(){
        return jpaUserRepository.count();
    }

    public List<User> getUsers(){
        return jpaUserRepository.findAll();
    }

    public User findUserById(String id){
        return jpaUserRepository.findByLoginId(id)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + id));
    }

    public User findUserByIdAndPassword(String id, String password){
        return jpaUserRepository.findByLoginIdAndPassword(id, password)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + id));
    }

    @Transactional
    public void updateUserInfo(String loginId, User modifiedUser){
        User realUser = jpaUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + loginId));

        realUser.updateUser(modifiedUser);
    }
}
