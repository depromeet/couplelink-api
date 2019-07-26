package com.depromeet.couplelink.service;

import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.entity.MemberDetail;

public interface MemberService {
    Member getOrCreateMember(String accessToken);

    Member getMemberById(Long memberId);

    Member getMemberForConnecting(Long memberId, String connectionNumber);

    MemberDetail getMemberDetailById(Long memberId);
}
