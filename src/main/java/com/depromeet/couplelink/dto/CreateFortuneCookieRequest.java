package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class CreateFortuneCookieRequest {
    /**
     * 쿠키 내용
     */
    @JsonProperty("message")
    @NotNull
    private String message;
}
