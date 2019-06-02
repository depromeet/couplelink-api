package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.MemberAssembler;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberAssembler memberAssembler;

    /**
     * 자기 자신 조회
     */
    @GetMapping("/api/members/me")
    public MemberResponse getMe(@ApiIgnore @RequestAttribute Long memberId,
                                @RequestHeader("Authorization") String authorization) {
        Member member = memberService.getMemberById(memberId);
        return memberAssembler.assembleMemberResponse(member);
    }

    /**
     * 멤버 상세 조회
     */
    @GetMapping("/api/members/{memberId:\\d+}")
    public MemberResponse getMember(@RequestHeader("Authorization") String authorization,
                                    @PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return memberAssembler.assembleMemberResponse(member);
    }
}
