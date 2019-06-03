package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.BannedTermResponse;
import com.depromeet.couplelink.entity.BannedTerm;
import com.depromeet.couplelink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BannedTermAssembler {
    private final MemberRepository memberRepository;
    private final MemberAssembler memberAssembler;

    @Transactional(readOnly = true)
    public BannedTermResponse assembleBannedTermResponse(BannedTerm bannedTerm) {
        final BannedTermResponse bannedTermResponse = new BannedTermResponse();
        bannedTermResponse.setId(bannedTerm.getId());
        bannedTermResponse.setName(bannedTerm.getName());
        bannedTermResponse.setCreatedAt(bannedTerm.getCreatedAt());
        bannedTermResponse.setWriterMemberResponse(Optional.ofNullable(bannedTerm.getWriterMemberId())
                .map(memberRepository::findById)
                .map(Optional::get)
                .map(memberAssembler::assembleMemberResponse)
                .orElse(null));
        bannedTermResponse.setCount(bannedTerm.getBannedTermLogs().size());
        return bannedTermResponse;
    }
}
