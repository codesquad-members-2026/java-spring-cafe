package com.codesquad.cafeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    @GetMapping("/")
    public String getQna(){

    }


}
