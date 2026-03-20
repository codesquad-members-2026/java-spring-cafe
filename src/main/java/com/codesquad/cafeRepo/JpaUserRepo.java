package com.codesquad.cafeRepo;

import com.codesquad.user.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JpaUserRepo extends JpaRepository<User,Integer> {

    public User getUserById(String id);

}
