package com.depromeet.couplelink.service;

import com.depromeet.couplelink.dto.BannedTermRequest;
import com.depromeet.couplelink.entity.BannedTerm;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannedTermService {
    BannedTerm createBannedTerm(Long memberId, Long coupleId, BannedTermRequest bannedTermRequest);

    List<BannedTerm> getBannedTerms(Long memberId, Long coupleId, Pageable pageable);

    BannedTerm getBannedTerm(Long memberId, Long coupleId, Long termId);

    void deleteBannedTerm(Long memberId, Long coupleId, Long termId);
}
