package com.codesquad;

import com.codesquad.cafeRepo.ArticleRepo;
import com.codesquad.cafeRepo.UserRepo;
import com.codesquad.service.ArticleService;
import com.codesquad.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public UserRepo userRepo(){
        return new UserRepo();
    }
     @Bean
    public UserService userService(){
        return new UserService(userRepo());
     }

     @Bean
    public ArticleRepo articleRepo(){
        return new ArticleRepo();
     }

     @Bean
    public ArticleService articleService(){
        return new ArticleService();
     }


}
