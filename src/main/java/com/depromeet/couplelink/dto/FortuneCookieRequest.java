package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FortuneCookieRequest {
    /**
     * 쿠키 내용
     */
    @JsonProperty("message")
    private String message;
}
