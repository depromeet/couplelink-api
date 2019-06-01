package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.dto.LoginRequest;
import com.depromeet.couplelink.dto.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    /**
     * 카카오 accessToken 으로 로그인
     *
     * @param loginRequest
     * @return couplelink accessToken
     */
    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return new LoginResponse();
    }
}
