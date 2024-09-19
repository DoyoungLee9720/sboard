package com.sboard.service;

import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.repository.UserRepository;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO) {
        // 회원가입
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        User user = userDTO.toEntity();
        userRepository.save(user);
    }
    //회원선택
    public UserDTO selectUser(String uid) {
        Optional<User> optUser = userRepository.findById(uid);
        if(optUser.isPresent()) {
            User user = optUser.get();
            return user.toDTO();
        }
        return null;
    }

    // 사용자 ID 중복 검사
    public boolean checkUserExists(String type, String value) {
        switch (type) {
            case "uid":
                return userRepository.existsById(value);
            case "nick":
                return userRepository.existsByNick(value);
            case "email":
                return userRepository.existsByEmail(value);
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
    public String sendEmailCode(String email) {
        // 인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);

        // 이메일 기본정보
        String title = "jboard 인증번호 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";
        String sender = "dlehdud224@gmail.com";
        String appPass = "andx arrh lzjz srlc"; // Google 앱 비밀번호

        // gmail SMTP 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // gmail session 생성
        Session gmailSession = Session.getInstance(props, new Authenticator(){
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sender, appPass);
            }
        });

        // 메일 발송
        Message message = new MimeMessage(gmailSession);

        try{
            message.setFrom(new InternetAddress(sender, "보내는사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=utf-8");
            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }

        return ""+code;
    }
}
