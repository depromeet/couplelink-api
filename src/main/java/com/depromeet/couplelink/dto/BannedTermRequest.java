package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BannedTermRequest {
    /**
     * 금지어
     */
    @JsonProperty("name")
    private String name;
}
