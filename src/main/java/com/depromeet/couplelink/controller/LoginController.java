package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.MemberAssembler;
import com.depromeet.couplelink.component.JwtFactory;
import com.depromeet.couplelink.dto.LoginRequest;
import com.depromeet.couplelink.dto.LoginResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final JwtFactory jwtFactory;

    /**
     * 카카오 accessToken 으로 로그인
     *
     * @param loginRequest
     * @return couplelink accessToken
     */
    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String accessToken = loginRequest.getAccessToken();
        Member member = memberService.getOrCreateMember(accessToken);
        String coupleLinkAccessToken = jwtFactory.generateToken(member);
        return LoginResponse.from(coupleLinkAccessToken);
    }
}
