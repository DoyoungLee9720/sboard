package com.sboard.controller;

import com.sboard.config.AppInfo;
import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.service.TermsService;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final TermsService termsService;
    private String checkEmailnumber;
    private final AppInfo appinfo;

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
            if(!exists&& type.equals("email")){
                checkEmailnumber = userService.sendEmailCode(value);
            }
            response.put("result", exists ? 1 : 0);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/checkemail")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, String> request) {
        String code = request.get("code"); // 클라이언트에서 보낸 'code' 값
        Map<String, Object> response = new HashMap<>();

        // 서버에 전송된 code 값과 checkEmailnumber 값 로그 출력
        System.out.println("Client Code: " + code);  // 클라이언트에서 받은 코드 출력
        System.out.println("Expected Code: " + checkEmailnumber);  // 서버에서 비교하는 인증 코드 출력

        // 인증코드 비교 로직
        if (checkEmailnumber.equals(code)) {
            response.put("result", 1);  // 이메일 인증 성공
        } else {
            response.put("result", 0);  // 이메일 인증 실패
        }

        // JSON 응답 반환
        return ResponseEntity.ok(response);
    }
}