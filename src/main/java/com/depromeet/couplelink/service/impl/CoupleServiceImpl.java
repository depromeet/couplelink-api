package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.dto.UpdateCoupleMemberRequest;
import com.depromeet.couplelink.entity.ChatRoom;
import com.depromeet.couplelink.entity.Couple;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.entity.MemberDetail;
import com.depromeet.couplelink.exception.ApiFailedException;
import com.depromeet.couplelink.repository.CoupleRepository;
import com.depromeet.couplelink.repository.MemberRepository;
import com.depromeet.couplelink.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements CoupleService {
    private final MemberRepository memberRepository;
    private final CoupleRepository coupleRepository;

    @Override
    @Transactional
    public Couple createCouple(Long memberId, String connectionNumber) {
        final Member me = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiFailedException("Member not found. memberId:" + memberId, HttpStatus.NOT_FOUND));
        if (!me.isSolo()) {
            throw new ApiFailedException("Member is already couple. memberId:" + me.getId(), HttpStatus.BAD_REQUEST);
        }
        final Member you = memberRepository.findByConnectionNumber(connectionNumber)
                .orElseThrow(() -> new ApiFailedException("Member not found. connectionNumber:" + connectionNumber, HttpStatus.NOT_FOUND));
        if (!you.isSolo()) {
            throw new ApiFailedException("Member is already couple. memberId:" + you.getId(), HttpStatus.BAD_REQUEST);
        }
        final ChatRoom chatRoom = new ChatRoom();

        final Couple couple = new Couple();
        couple.setChatRoom(chatRoom);
        couple.setMembers(Arrays.asList(me, you));
        coupleRepository.save(couple);
        me.setCouple(couple);
        you.setCouple(couple);
        return couple;
    }

    @Override
    @Transactional
    public Couple createOrUpdateMemberDetail(Long memberId, Long coupleId, UpdateCoupleMemberRequest updateCoupleMemberRequest) {
        final Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND));
        final Member member = couple.getMembers().stream()
                .filter(m -> memberId.equals(m.getId()))
                .findFirst()
                .orElseThrow(() -> new ApiFailedException("Member not found from couple. memberId:" + memberId + ", coupleId: " + coupleId, HttpStatus.BAD_REQUEST));
        final MemberDetail memberDetail = Optional.ofNullable(member.getMemberDetail()).orElse(new MemberDetail());
        memberDetail.setName(updateCoupleMemberRequest.getName());
        memberDetail.setGenderType(updateCoupleMemberRequest.getGenderType());
        memberDetail.setProfileImageUrl(updateCoupleMemberRequest.getProfileImageUrl());
        memberDetail.setBirthDate(updateCoupleMemberRequest.getBirthDate());
        member.setMemberDetail(memberDetail);
        couple.setStartedAt(updateCoupleMemberRequest.getStartedAt());
        couple.updateConnectionStatus();
        return couple;
    }
}
