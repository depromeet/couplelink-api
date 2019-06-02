package com.depromeet.couplelink.service;

import com.depromeet.couplelink.entity.Couple;
import com.depromeet.couplelink.entity.MemberDetail;

public interface CoupleService {
    Couple createCouple(Long memberId, Long targetMemberId);

    Couple addMember(Long memberId, MemberDetail memberDetail);
}
