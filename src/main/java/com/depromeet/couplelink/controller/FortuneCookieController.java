package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.dto.FortuneCookieRequest;
import com.depromeet.couplelink.dto.FortuneCookieResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FortuneCookieController {
    /**
     * 쿠키 목록 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/cookies")
    public List<FortuneCookieResponse> getCookies(@PathVariable Long coupleId,
                                                  @RequestParam List<String> writer,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size) {
        return new ArrayList<>();
    }

    /**
     * 쿠키 상세 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/cookies/{cookieId:\\d+}")
    public FortuneCookieResponse getCookie(@PathVariable Long coupleId,
                                           @PathVariable Long cookieId) {
        return new FortuneCookieResponse();
    }

    /**
     * 쿠키 생성
     */
    @PostMapping("/api/couples/{coupleId:\\d+}/cookies")
    @ResponseStatus(HttpStatus.CREATED)
    public FortuneCookieResponse createCookie(@RequestBody FortuneCookieRequest fortuneCookieRequest) {
        return new FortuneCookieResponse();
    }

    /**
     * 쿠키 삭제
     */
    @DeleteMapping("/api/couples/{coupleId:\\d+}/cookies/{cookieId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCookie(@PathVariable Long coupleId,
                             @PathVariable Long cookieId) {

    }
}
