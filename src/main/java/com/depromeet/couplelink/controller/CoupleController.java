package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.dto.CoupleRequest;
import com.depromeet.couplelink.dto.CoupleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class CoupleController {
    /**
     * 상대방의 연결 번호를 입력하고, 그 상대와 커플이 됩니다.
     * 내가 이미 커플인 경우 400
     * 상대방이 이미 커플인 경우 400
     * 연결 번호에 해당하는 상대가 없는 경우, 400
     *
     * @param coupleRequest 상대방 연결 번호
     * @return 커플 정보
     */
    @PostMapping("/api/couples")
    @ResponseStatus(HttpStatus.CREATED)
    public CoupleResponse createCouple(@ApiIgnore @RequestAttribute Long memberId,
                                       @RequestHeader("Authorization") String authorization,
                                       @RequestBody CoupleRequest coupleRequest) {
        return new CoupleResponse();
    }

    /**
     * 아이디로 커플을 조회합니다.
     *
     * @param coupleId 커플 아이디
     * @return 커플 정보
     */
    @GetMapping("/api/couples/{coupleId:\\d+}")
    public CoupleResponse getCouple(@ApiIgnore @RequestAttribute Long memberId,
                                    @RequestHeader("Authorization") String authorization,
                                    @PathVariable Long coupleId) {
        return new CoupleResponse();
    }
}
