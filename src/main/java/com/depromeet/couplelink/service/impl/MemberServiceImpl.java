package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.kakao.KakaoUserResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.entity.ProviderType;
import com.depromeet.couplelink.exception.ApiFailedException;
import com.depromeet.couplelink.repository.MemberRepository;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final KakaoAdapter kakaoAdapter;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member getOrCreateMember(String accessToken) {
        final KakaoUserResponse kakaoUserResponse = kakaoAdapter.getUserInfo(accessToken);
        if (kakaoUserResponse == null) {
            throw new ApiFailedException("Failed to get user info from kakao api", HttpStatus.SERVICE_UNAVAILABLE);
        }
        final String providerUserId = kakaoUserResponse.getId().toString();

        return memberRepository.findByProviderTypeAndProviderUserId(ProviderType.KAKAO, providerUserId)
                .orElseGet(() -> {
                    final Member member = new Member();
                    member.setConnectionNumber(UUID.randomUUID().toString());
                    member.setProviderType(ProviderType.KAKAO);
                    member.setProviderUserId(providerUserId);
                    return memberRepository.save(member);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiFailedException("Member not found.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberForConnecting(Long memberId, String connectionNumber) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(connectionNumber, "'connectionNumber' must not be null");

        final Member member = memberRepository.findByConnectionNumber(connectionNumber)
                .orElseThrow(() -> new ApiFailedException("Member not found.", HttpStatus.NOT_FOUND));
        if (!member.isSolo()) {
            throw new ApiFailedException("The member is already connected with someone.", HttpStatus.FORBIDDEN);
        }
        if (memberId.equals(member.getId())) {
            throw new ApiFailedException("It is not allowed to connect with myself", HttpStatus.BAD_REQUEST);
        }
        return member;
    }
}
