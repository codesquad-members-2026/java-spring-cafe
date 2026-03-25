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

    // TODO: 현재 수정 사항이 DB에 반영되지만 user/modify에 들어가면 회원가입 초기 데이터가 나타남 -> DB 정보를 다시 끌어오는 로직 필요
    // TODO: 루카스에 올라온 회원수정 요구사항을 따르기
    @Transactional
    public void updateUserInfo(String loginId, User modifiedUser){
        User realUser = jpaUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserInfoCannotBeFoundException("No user found with id " + loginId));

        realUser.updateUser(modifiedUser);
    }
}
