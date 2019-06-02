package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class CoupleRequest {
    /**
     * 상대방 회원 번호
     */
    @JsonProperty("memberId")
    @NotNull
    private Long memberId;
}
