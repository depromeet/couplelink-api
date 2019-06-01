package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginResponse {
    /**
     * 우리 서비스 accessToken
     */
    @JsonProperty("accessToken")
    private String accessToken;
}
