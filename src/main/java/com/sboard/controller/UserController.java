package com.sboard.controller;

import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.service.TermsService;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final TermsService termsService;

    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        List<TermsDTO> termsList = termsService.selectTerms();
        if (!termsList.isEmpty()) {
            TermsDTO terms = termsList.get(0); // 첫 번째 항목
            model.addAttribute("term", terms.getTerms());  // terms 필드 값 추가
            model.addAttribute("privacy", terms.getPrivacy());  // privacy 필드 값 추가
        }
        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(){
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(UserDTO userDTO, @RequestParam("pass1") String pass1){
        userDTO.setPass(pass1);
        userService.insertUser(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestParam("type") String type, @RequestParam("value") String value) {

        Map<String, Object> response = new HashMap<>();

        try {
            boolean exists = userService.checkUserExists(type, value);
            response.put("result", exists ? 1 : 0);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}