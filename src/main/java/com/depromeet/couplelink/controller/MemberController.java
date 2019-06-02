package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.MemberAssembler;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.List;

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
        final Member member = memberService.getMemberById(memberId);
        return memberAssembler.assembleMemberResponse(member);
    }

    /**
     * 멤버 상세 조회
     */
    @GetMapping("/api/members/{memberId:\\d+}")
    public MemberResponse getMember(@RequestHeader("Authorization") String authorization,
                                    @PathVariable Long memberId) {
        final Member member = memberService.getMemberById(memberId);
        return memberAssembler.assembleMemberResponse(member);
    }

    /**
     * 연결 번호로 멤버 조회
     */
    @GetMapping("/api/members")
    public List<MemberResponse> getMemberByConnectionNumber(@ApiIgnore @RequestAttribute Long memberId,
                                                            @RequestHeader("Authorization") String authorization,
                                                            @RequestParam String connectionNumber) {
        final Member member = memberService.getMemberForConnecting(memberId, connectionNumber);
        final MemberResponse memberResponse = memberAssembler.assembleMemberResponse(member);
        return Collections.singletonList(memberResponse);
    }
}
