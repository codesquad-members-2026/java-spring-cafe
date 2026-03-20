package com.codesquad;

import com.codesquad.cafeRepo.ArticleRepo;
import com.codesquad.cafeRepo.JpaArticleRepo;
import com.codesquad.cafeRepo.JpaUserRepo;
import com.codesquad.service.ArticleService;
import com.codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final JpaUserRepo userRepository;
    private final JpaArticleRepo articleRepository;

    @Autowired
    public SpringConfig( JpaUserRepo userRepo, JpaArticleRepo articleRepo){
        this.userRepository = userRepo;
        this.articleRepository = articleRepo;
    }

     @Bean
    public UserService userService(){
        return new UserService(userRepository);
     }



     @Bean
    public ArticleService articleService(){
        return new ArticleService(articleRepository);
     }


}
