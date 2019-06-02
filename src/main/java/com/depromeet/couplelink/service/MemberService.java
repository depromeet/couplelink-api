package com.depromeet.couplelink.service;

import com.depromeet.couplelink.entity.Member;

public interface MemberService {
    Member getOrCreateMember(String accessToken);
    Member getMemberById(Long memberId);
}
