package com.depromeet.couplelink.service;

import com.depromeet.couplelink.dto.UpdateCoupleMemberRequest;
import com.depromeet.couplelink.entity.Couple;

public interface CoupleService {
    Couple createCouple(Long memberId, Long targetMemberId);

    Couple createOrUpdateMemberDetail(Long memberId, Long coupleId, UpdateCoupleMemberRequest updateCoupleMemberRequest);
}
