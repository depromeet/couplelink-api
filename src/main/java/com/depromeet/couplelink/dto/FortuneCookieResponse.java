package com.depromeet.couplelink.dto;

import com.depromeet.couplelink.model.ReadStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
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
    private MemberResponse writerMemberResponse;

    /**
     * 쿠키 생성 시간
     */
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    /**
     * (상대가) 쿠키 읽음 여부
     */
    @JsonProperty("status")
    private ReadStatus readStatus;

    /**
     * (상대가) 쿠키 읽은 시간
     */
    @JsonProperty("readAt")
    private LocalDateTime readAt;
}
