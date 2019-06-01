package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {
    /**
     * 카카오 api accessToken
     */
    @JsonProperty("accessToken")
    private String accessToken;
}
