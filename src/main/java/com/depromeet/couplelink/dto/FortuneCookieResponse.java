package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class FortuneCookieResponse {
    /**
     * 쿠키 아이디
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 쿠키 내용
     */
    @JsonProperty("message")
    private String message;

    /**
     * 쿠키 쓴 사람
     */
    @JsonProperty("writer")
    private MemberResponse memberResponse;

    /**
     * 쿠키 생성 시간
     */
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
