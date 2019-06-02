package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.model.MemberStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MemberAssembler {
    public MemberResponse assembleMemberResponse(Member member) {
        Assert.notNull(member, "'member' must not be null");

        final MemberResponse memberResponse = new MemberResponse();
        memberResponse.setId(member.getId());
        memberResponse.setName(member.getName());
        memberResponse.setConnectionNumber(member.getConnectionNumber());
        memberResponse.setProfileImageUrl(member.getProfileImageUrl());
        memberResponse.setCoupleId(member.getCoupleId().orElse(null));
        memberResponse.setMemberStatus(memberResponse.getCoupleId() == null ? MemberStatus.SOLO : MemberStatus.COUPLE);
        return memberResponse;
    }
}
