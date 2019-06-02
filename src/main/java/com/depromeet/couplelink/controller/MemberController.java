package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.MemberAssembler;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberAssembler memberAssembler;

    /**
     * 자기 자신 조회
     */
    @GetMapping("/api/members/me")
    public MemberResponse getMe(@RequestAttribute Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return memberAssembler.assembleMemberResponse(member);
    }

    /**
     * 멤버 상세 조회
     */
    @GetMapping("/api/members/{memberId:\\d+}")
    public MemberResponse getMember(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return memberAssembler.assembleMemberResponse(member);
    }
}
