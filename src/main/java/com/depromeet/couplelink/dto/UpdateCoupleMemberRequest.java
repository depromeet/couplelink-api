package com.depromeet.couplelink.dto;

import com.depromeet.couplelink.model.stereotype.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class UpdateCoupleMemberRequest {
    /**
     * 이름
     */
    @JsonProperty("name")
    @NotNull
    private String name;
    /**
     * 성별
     */
    @JsonProperty("genderType")
    @NotNull
    private GenderType genderType;
    /**
     * 생일
     */
    @JsonProperty("birthDate")
    @NotNull
    private String birthDate;
    /**
     * 프로필 이미지 url
     */
    @JsonProperty("profileImageUrl")
    @NotNull
    private String profileImageUrl;
    /**
     * 기념일
     */
    @JsonProperty("startedAt")
    @NotNull
    private String startedAt;
}
