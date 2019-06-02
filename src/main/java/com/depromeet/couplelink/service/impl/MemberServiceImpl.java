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
                    member.setName(kakaoUserResponse.getUserName());
                    member.setProfileImageUrl(kakaoUserResponse.getProfileImage());
                    member.setConnectionNumber(UUID.randomUUID().toString());
                    member.setProviderType(ProviderType.KAKAO);
                    member.setProviderUserId(providerUserId);
                    return memberRepository.save(member);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiFailedException("Member not found.", HttpStatus.NOT_FOUND));
    }
}
