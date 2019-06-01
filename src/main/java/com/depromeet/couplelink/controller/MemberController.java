package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.dto.MemberResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    /**
     * 자기 자신 조회
     */
    @GetMapping("/api/members/me")
    public MemberResponse getMe() {
        return new MemberResponse();
    }

    /**
     * 멤버 상세 조회
     */
    @GetMapping("/api/members/{memberId:\\d+}")
    public MemberResponse getMember(@PathVariable Long memberId) {
        return new MemberResponse();
    }
}
