package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.FortuneCookieAssembler;
import com.depromeet.couplelink.dto.CreateFortuneCookieRequest;
import com.depromeet.couplelink.dto.FortuneCookieResponse;
import com.depromeet.couplelink.entity.FortuneCookie;
import com.depromeet.couplelink.model.stereotype.WriterType;
import com.depromeet.couplelink.service.FortuneCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FortuneCookieController {
    private final FortuneCookieService fortuneCookieService;
    private final FortuneCookieAssembler fortuneCookieAssembler;

    /**
     * 쿠키 목록 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/cookies")
    public List<FortuneCookieResponse> getCookies(@ApiIgnore @RequestAttribute Long memberId,
                                                  @RequestHeader("Authorization") String authorization,
                                                  @PathVariable Long coupleId,
                                                  @RequestParam(name = "writer", defaultValue = "ALL") WriterType writerType,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size) {
        final Pageable pageable = PageRequest.of(page, size);
        return fortuneCookieService.getCookies(memberId, coupleId, writerType, pageable).stream()
                .map(fortuneCookieAssembler::assembleFortuneCookieResponse)
                .collect(Collectors.toList());
    }

    /**
     * 쿠키 상세 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/cookies/{cookieId:\\d+}")
    public FortuneCookieResponse getCookie(@ApiIgnore @RequestAttribute Long memberId,
                                           @RequestHeader("Authorization") String authorization,
                                           @PathVariable Long coupleId,
                                           @PathVariable Long cookieId) {
        final FortuneCookie fortuneCookie = fortuneCookieService.getCookie(memberId, coupleId, cookieId);
        return fortuneCookieAssembler.assembleFortuneCookieResponse(fortuneCookie);
    }

    /**
     * 쿠키 생성
     */
    @PostMapping("/api/couples/{coupleId:\\d+}/cookies")
    @ResponseStatus(HttpStatus.CREATED)
    public FortuneCookieResponse createCookie(@ApiIgnore @RequestAttribute Long memberId,
                                              @RequestHeader("Authorization") String authorization,
                                              @PathVariable Long coupleId,
                                              @RequestBody @Valid CreateFortuneCookieRequest createFortuneCookieRequest) {
        final FortuneCookie fortuneCookie = fortuneCookieService.createCookie(memberId, coupleId, createFortuneCookieRequest);
        return fortuneCookieAssembler.assembleFortuneCookieResponse(fortuneCookie);
    }

    /**
     * 쿠키 삭제
     */
    @DeleteMapping("/api/couples/{coupleId:\\d+}/cookies/{cookieId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCookie(@ApiIgnore @RequestAttribute Long memberId,
                             @RequestHeader("Authorization") String authorization,
                             @PathVariable Long coupleId,
                             @PathVariable Long cookieId) {
        fortuneCookieService.deleteCookie(memberId, coupleId, cookieId);
    }

    /**
     * 쿠키 읽음 처리
     */
    @PostMapping("/api/couples/{coupleId:\\d+}/cookies/{cookieId:\\d+}/read")
    public FortuneCookieResponse readCookie(@ApiIgnore @RequestAttribute Long memberId,
                                            @RequestHeader("Authorization") String authorization,
                                            @PathVariable Long coupleId,
                                            @PathVariable Long cookieId) {
        final FortuneCookie fortuneCookie = fortuneCookieService.setCookieAsRead(memberId, coupleId, cookieId);
        return fortuneCookieAssembler.assembleFortuneCookieResponse(fortuneCookie);
    }
}
