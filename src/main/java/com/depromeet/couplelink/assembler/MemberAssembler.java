package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.entity.MemberDetail;
import com.depromeet.couplelink.model.IndexRange;
import com.depromeet.couplelink.model.MemberStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class MemberAssembler {
    public MemberResponse assembleMemberResponse(Member member) {
        Assert.notNull(member, "'member' must not be null");

        final MemberResponse memberResponse = new MemberResponse();
        memberResponse.setId(member.getId());
        memberResponse.setConnectionNumber(member.getConnectionNumber());
        memberResponse.setCoupleId(member.getCoupleId().orElse(null));
        memberResponse.setMemberStatus(memberResponse.getCoupleId() == null ? MemberStatus.SOLO : MemberStatus.COUPLE);

        final MemberDetail memberDetail = member.getMemberDetail();
        memberResponse.setName(memberDetail != null ? memberDetail.getName() : null);
        memberResponse.setProfileImageUrl(memberDetail != null ? memberDetail.getProfileImageUrl() : null);
        memberResponse.setBirthDate(memberDetail != null ? memberDetail.getBirthDate() : null);
        return memberResponse;
    }
}
