package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.BannedTermAssembler;
import com.depromeet.couplelink.dto.BannedTermRequest;
import com.depromeet.couplelink.dto.BannedTermResponse;
import com.depromeet.couplelink.entity.BannedTerm;
import com.depromeet.couplelink.service.BannedTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BannedTermController {
    private final BannedTermService bannedTermService;
    private final BannedTermAssembler bannedTermAssembler;

    /**
     * 금지어 목록 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/banned-terms")
    public List<BannedTermResponse> getBannedTerms(@ApiIgnore @RequestAttribute Long memberId,
                                                   @RequestHeader("Authorization") String authorization,
                                                   @PathVariable Long coupleId,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "20") Integer size) {
        final Pageable pageable = PageRequest.of(page, size);
        return bannedTermService.getBannedTerms(memberId, coupleId, pageable).stream()
                .map(bannedTermAssembler::assembleBannedTermResponse)
                .collect(Collectors.toList());
    }

    /**
     * 금지어 상세 조회
     */
    @GetMapping("/api/couples/{coupleId:\\d+}/banned-terms/{bannedTermId:\\d+}")
    public BannedTermResponse getBannedTerm(@ApiIgnore @RequestAttribute Long memberId,
                                            @RequestHeader("Authorization") String authorization,
                                            @PathVariable Long coupleId,
                                            @PathVariable Long bannedTermId) {
        final BannedTerm bannedTerm = bannedTermService.getBannedTerm(memberId, coupleId, bannedTermId);
        return bannedTermAssembler.assembleBannedTermResponse(bannedTerm);
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
        final BannedTerm bannedTerm = bannedTermService.createBannedTerm(memberId, coupleId, bannedTermRequest);
        return bannedTermAssembler.assembleBannedTermResponse(bannedTerm);
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
        bannedTermService.deleteBannedTerm(memberId, coupleId, bannedTermId);
    }
}
