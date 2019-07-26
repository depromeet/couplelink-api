package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.dto.BannedTermRequest;
import com.depromeet.couplelink.entity.BannedTerm;
import com.depromeet.couplelink.entity.Couple;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.exception.ApiFailedException;
import com.depromeet.couplelink.repository.BannedTermRepository;
import com.depromeet.couplelink.repository.CoupleRepository;
import com.depromeet.couplelink.service.BannedTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannedTermServiceImpl implements BannedTermService {
    private final BannedTermRepository bannedTermRepository;
    private final CoupleRepository coupleRepository;

    @Override
    @Transactional
    public BannedTerm createBannedTerm(Long memberId, Long coupleId, BannedTermRequest bannedTermRequest) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(bannedTermRequest, "'bannedTermRequest' must not be null");

        this.checkAuthority(memberId, coupleId);

        return bannedTermRepository.findByCoupleIdAndName(coupleId, bannedTermRequest.getName())
                .map(bannedTerm -> {
                    if (bannedTerm.getDeleted()) {
                        bannedTerm.setDeleted(false);
                    }
                    return bannedTerm;
                })
                .orElseGet(() -> {
                    final BannedTerm bannedTerm = new BannedTerm();
                    bannedTerm.setCoupleId(coupleId);
                    bannedTerm.setName(bannedTermRequest.getName());
                    bannedTerm.setWriterMemberId(memberId);
                    bannedTerm.setDeleted(false);
                    return bannedTermRepository.save(bannedTerm);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannedTerm> getBannedTerms(Long memberId, Long coupleId, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        this.checkAuthority(memberId, coupleId);

        return bannedTermRepository.findByCoupleId(coupleId, pageable).stream()
                .filter(BannedTerm::isNotDeleted)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BannedTerm getBannedTerm(Long memberId, Long coupleId, Long termId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(termId, "'termId' must not be null");

        this.checkAuthority(memberId, coupleId);

        return bannedTermRepository.findByIdAndCoupleId(termId, coupleId)
                .filter(BannedTerm::isNotDeleted)
                .orElseThrow(() -> new ApiFailedException("BannedTerm not found. coupleId: " + coupleId + "termId: " + termId, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteBannedTerm(Long memberId, Long coupleId, Long termId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(termId, "'termId' must not be null");

        this.checkAuthority(memberId, coupleId);

        bannedTermRepository.findByIdAndCoupleId(termId, coupleId)
                .filter(BannedTerm::isNotDeleted)
                .ifPresent(bannedTerm -> {
                    bannedTerm.setDeleted(true);
                    bannedTermRepository.save(bannedTerm);
                });
    }

    private Couple checkAuthority(Long memberId, Long coupleId) {
        final Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND));
        if (couple.getMembers().stream()
                .map(Member::getId)
                .noneMatch(id -> id.equals(memberId))) {
            throw new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND);
        }
        return couple;
    }
}
