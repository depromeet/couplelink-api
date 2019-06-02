package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.dto.CreateFortuneCookieRequest;
import com.depromeet.couplelink.entity.Couple;
import com.depromeet.couplelink.entity.FortuneCookie;
import com.depromeet.couplelink.entity.FortuneCookieReceipt;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.exception.ApiFailedException;
import com.depromeet.couplelink.model.ReadStatus;
import com.depromeet.couplelink.model.stereotype.WriterType;
import com.depromeet.couplelink.repository.CoupleRepository;
import com.depromeet.couplelink.repository.FortuneCookieReceiptRepository;
import com.depromeet.couplelink.repository.FortuneCookieRepository;
import com.depromeet.couplelink.service.FortuneCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FortuneCookieServiceImpl implements FortuneCookieService {
    private final CoupleRepository coupleRepository;
    private final FortuneCookieRepository fortuneCookieRepository;
    private final FortuneCookieReceiptRepository fortuneCookieReceiptRepository;

    @Override
    @Transactional
    public FortuneCookie createCookie(Long memberId, Long coupleId, CreateFortuneCookieRequest createFortuneCookieRequest) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(createFortuneCookieRequest, "'createFortuneCookieRequest' must not be null");

        final Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND));
        final FortuneCookie fortuneCookie = new FortuneCookie();
        fortuneCookie.setMessage(createFortuneCookieRequest.getMessage());
        fortuneCookie.setCoupleId(coupleId);
        fortuneCookie.setWriterMemberId(memberId);

        final FortuneCookieReceipt fortuneCookieReceipt = new FortuneCookieReceipt();
        fortuneCookieReceipt.setCoupleId(coupleId);
        fortuneCookieReceipt.setFortuneCookie(fortuneCookie);
        fortuneCookieReceipt.setStatus(ReadStatus.UNREAD);
        fortuneCookieReceipt.setReaderMemberId(couple.getYourMemberId(memberId));

        fortuneCookie.setFortuneCookieReceipt(fortuneCookieReceipt);
        return fortuneCookieRepository.save(fortuneCookie);
    }

    @Override
    @Transactional(readOnly = true)
    public FortuneCookie getCookie(Long memberId, Long coupleId, Long cookieId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(cookieId, "'cookieId' must not be null");

        this.checkAuthority(memberId, coupleId);
        return fortuneCookieRepository.findById(cookieId)
                .orElseThrow(() -> new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FortuneCookie> getCookies(Long memberId, Long coupleId, WriterType writerType, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(writerType, "'writerType' must not be null");

        final Couple couple = this.checkAuthority(memberId, coupleId);
        final List<Long> writerMemberIds = this.getWriterMemberIds(memberId, couple, writerType);
        return fortuneCookieRepository.findByCoupleIdAndWriterMemberIdIn(coupleId, writerMemberIds, pageable).getContent();
    }

    @Override
    @Transactional
    public void deleteCookie(Long memberId, Long coupleId, Long cookieId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(coupleId, "'coupleId' must not be null");
        Assert.notNull(cookieId, "'cookieId' must not be null");

        this.checkAuthority(memberId, coupleId);
        fortuneCookieRepository.findByIdAndCoupleId(cookieId, coupleId)
                .ifPresent(fortuneCookieRepository::delete);
    }

    @Override
    @Transactional
    public FortuneCookie setCookieAsRead(Long memberId, Long coupleId, Long cookieId) {
        this.checkAuthority(memberId, coupleId);
        final FortuneCookie fortuneCookie = fortuneCookieRepository.findByIdAndCoupleId(cookieId, coupleId)
                .orElseThrow(() -> new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND));
        final FortuneCookieReceipt fortuneCookieReceipt = fortuneCookie.getFortuneCookieReceipt();
        if (fortuneCookieReceipt == null) {
            throw new ApiFailedException("fortuneCookieReceipt not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (fortuneCookieReceipt.getStatus() == ReadStatus.UNREAD) {
            fortuneCookieReceipt.setStatus(ReadStatus.READ);
            fortuneCookieReceipt.setReadAt(LocalDateTime.now());
            fortuneCookieReceiptRepository.save(fortuneCookieReceipt);
        }
        return fortuneCookie;
    }

    private Couple checkAuthority(Long memberId, Long coupleId) {
        final Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new ApiFailedException("Couple not found", HttpStatus.NOT_FOUND));
        if (couple.getMembers().stream()
                .map(Member::getId)
                .noneMatch(id -> id.equals(memberId))) {
            throw new ApiFailedException("Cookie not found", HttpStatus.NOT_FOUND);
        }
        return couple;
    }

    private List<Long> getWriterMemberIds(Long memberId, Couple couple, WriterType writerType) {
        switch (writerType) {
            case ME:
                return Collections.singletonList(memberId);
            case YOU:
                return Collections.singletonList(couple.getYourMemberId(memberId));
            case ALL:
                return Arrays.asList(memberId, couple.getYourMemberId(memberId));
            default:
                throw new IllegalArgumentException("'writerType' is not supported. writerType:" + writerType);
        }
    }
}
