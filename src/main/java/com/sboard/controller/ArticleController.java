package com.sboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

    @GetMapping("/article/list")
    public String list(@RequestParam("uid") String uid, @RequestParam("pass") String pass, Model model) {
        model.addAttribute("uid", uid);
        model.addAttribute("pass", pass);
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
