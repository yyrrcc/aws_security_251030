package com.example.aws_security_251030.controller;

import com.example.aws_security_251030.entity.User;
import com.example.aws_security_251030.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 회원가입 (@RequestParam) - 중복된 아이디 반환해주지 않음
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            return userService.signupUser(username, password);
        } catch (RuntimeException e) {
            // 여기서 중복 메시지 판단 후 409 상태 반환
            if (e.getMessage().contains("이미 존재하는 username")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        }
    }


    // 로그인 인증 받은 유저만 접근 가능할 수 있는 요청
    @GetMapping("/check")
    public String checkUsername() {
        System.out.println("로그인 확인됨");
        return "로그인 성공 확인";
    }
    
    // 현재 로그인한 사용자 정보 확인
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인한 사용자가 없습니다."));
        }
        return ResponseEntity.ok(Map.of("username", auth.getName()));
    }
    

}
