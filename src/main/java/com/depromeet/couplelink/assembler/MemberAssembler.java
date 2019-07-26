package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.model.MemberStatus;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAssembler {
    private final MemberService memberService;

    public MemberResponse assembleMemberResponse(Member member) {
        Assert.notNull(member, "'member' must not be null");

        final MemberResponse memberResponse = new MemberResponse();
        memberResponse.setId(member.getId());
        memberResponse.setConnectionNumber(member.getConnectionNumber());
        memberResponse.setCoupleId(member.getCoupleId().orElse(null));
        memberResponse.setMemberStatus(memberResponse.getCoupleId() == null ? MemberStatus.SOLO : MemberStatus.COUPLE);

        memberService.getMemberDetailById(member.getId())
                .ifPresent(memberDetail -> {
                    memberResponse.setName(memberDetail.getName());
                    memberResponse.setProfileImageUrl(memberDetail.getProfileImageUrl());
                    memberResponse.setBirthDate(memberDetail.getBirthDate());
                });
        return memberResponse;
    }
}
