package com.sboard.controller;

import com.sboard.entity.User;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final UserService userService;

    @GetMapping("/article/list")
    public String list() {
        return "/article/list";
    }

    @GetMapping("/article/write")
    public String write(){
        return "/article/write";
    }

    @GetMapping("/article/view")
    public String view(){
        return "/article/view";
    }

    @GetMapping("/article/modify")
    public String modify(){
        return "/article/modify";
    }
}
