package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class CoupleRequest {
    /**
     * 상대방 연결 번호
     */
    @JsonProperty("connectionNumber")
    @NotNull
    private String connectionNumber;
}
