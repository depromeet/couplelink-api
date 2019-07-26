package com.depromeet.couplelink.service;

import com.depromeet.couplelink.dto.UpdateCoupleMemberRequest;
import com.depromeet.couplelink.entity.Couple;

import java.util.List;

public interface CoupleService {
    Couple createCouple(Long memberId, String connectionNumber);

    Couple createOrUpdateMemberDetail(Long memberId, Long coupleId, UpdateCoupleMemberRequest updateCoupleMemberRequest);

    List<Couple> getCouples();

    Couple getCouple(Long coupleId);
}
