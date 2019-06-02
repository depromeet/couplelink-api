package com.depromeet.couplelink.service;

import com.depromeet.couplelink.dto.CreateFortuneCookieRequest;
import com.depromeet.couplelink.entity.FortuneCookie;
import com.depromeet.couplelink.model.stereotype.WriterType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FortuneCookieService {
    FortuneCookie createCookie(Long memberId, Long coupleId, CreateFortuneCookieRequest createFortuneCookieRequest);

    FortuneCookie getCookie(Long memberId, Long coupleId, Long cookieId);

    List<FortuneCookie> getCookies(Long memberId, Long coupleId, WriterType writerType, Pageable pageable);

    void deleteCookie(Long memberId, Long coupleId, Long cookieId);

    FortuneCookie setCookieAsRead(Long memberId, Long coupleId, Long cookieId);
}
