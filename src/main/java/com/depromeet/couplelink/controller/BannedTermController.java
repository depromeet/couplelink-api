package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.dto.BannedTermRequest;
import com.depromeet.couplelink.dto.BannedTermResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BannedTermController {
    /**
     * 금지어 목록 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/banned-terms")
    public List<BannedTermResponse> getBannedTerms(@ApiIgnore @RequestAttribute Long memberId,
                                                   @RequestHeader("Authorization") String authorization,
                                                   @PathVariable Long coupleId,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "20") Integer size) {
        return new ArrayList<>();
    }

    /**
     * 금지어 상세 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/banned-terms/{bannedTermId:\\d+}")
    public BannedTermResponse getBannedTerm(@ApiIgnore @RequestAttribute Long memberId,
                                            @RequestHeader("Authorization") String authorization,
                                            @PathVariable Long coupleId,
                                            @PathVariable Long bannedTermId) {
        return new BannedTermResponse();
    }

    /**
     * 금지어 추가
     */
    @PostMapping("/api/couples/{coupleId:\\d+}/banned-terms")
    @ResponseStatus(HttpStatus.CREATED)
    public BannedTermResponse createBannedTerm(@ApiIgnore @RequestAttribute Long memberId,
                                               @RequestHeader("Authorization") String authorization,
                                               @PathVariable Long coupleId,
                                               @RequestBody BannedTermRequest bannedTermRequest) {
        return new BannedTermResponse();
    }

    /**
     * 금지어 삭제
     */
    @DeleteMapping("/api/couples/{coupleId:\\d+}/banned-terms/{bannedTermId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBannedTerm(@ApiIgnore @RequestAttribute Long memberId,
                                 @RequestHeader("Authorization") String authorization,
                                 @PathVariable Long coupleId,
                                 @PathVariable Long bannedTermId) {
    }
}
