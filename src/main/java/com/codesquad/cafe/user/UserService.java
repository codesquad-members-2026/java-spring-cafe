package com.codesquad.cafe.user;

import com.codesquad.cafe.exception.UserInfoCannotBeFoundException;
import com.codesquad.cafe.user.dto.UserUpdateDTO;
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

    // TODO: id로 매핑 후 loginId를 비교 -> findById()로 대체 가능
    public User findUserById(String id){
        return jpaUserRepository.findByLoginId(id)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + id));
    }

    public User findUserByIdAndPassword(String id, String password){
        return jpaUserRepository.findByLoginIdAndPassword(id, password)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + id));
    }

    @Transactional
    public User updateUserInfo(Long id, UserUpdateDTO updateDTO){
        User result = jpaUserRepository.findById(id)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + id));

        return result.updateUser(updateDTO);
    }
}
