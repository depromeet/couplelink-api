package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.FortuneCookieResponse;
import com.depromeet.couplelink.entity.FortuneCookie;
import com.depromeet.couplelink.entity.FortuneCookieReceipt;
import com.depromeet.couplelink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FortuneCookieAssembler {
    private final MemberAssembler memberAssembler;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public FortuneCookieResponse assembleFortuneCookieResponse(FortuneCookie fortuneCookie) {
        final FortuneCookieResponse fortuneCookieResponse = new FortuneCookieResponse();
        fortuneCookieResponse.setId(fortuneCookie.getId());
        fortuneCookieResponse.setMessage(fortuneCookie.getMessage());
        fortuneCookieResponse.setCreatedAt(fortuneCookie.getCreatedAt());
        fortuneCookieResponse.setWriterMemberResponse(Optional.ofNullable(fortuneCookie.getWriterMemberId())
                .map(memberRepository::findById)
                .map(Optional::get)
                .map(memberAssembler::assembleMemberResponse)
                .orElse(null));
        fortuneCookieResponse.setReadStatus(Optional.ofNullable(fortuneCookie.getFortuneCookieReceipt())
                .map(FortuneCookieReceipt::getStatus)
                .orElse(null));
        fortuneCookieResponse.setReadAt(Optional.ofNullable(fortuneCookie.getFortuneCookieReceipt())
                .map(FortuneCookieReceipt::getReadAt)
                .orElse(null));
        return fortuneCookieResponse;
    }
}
