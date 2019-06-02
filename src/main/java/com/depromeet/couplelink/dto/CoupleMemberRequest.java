package com.depromeet.couplelink.dto;

import com.depromeet.couplelink.model.stereotype.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CoupleMemberRequest {
    /**
     * 멤버 아이디
     */
    @JsonProperty("memberId")
    private Long memberId;
    /**
     * 이름
     */
    @JsonProperty("name")
    private String name;
    /**
     * 성별
     */
    @JsonProperty("genderType")
    private GenderType genderType;
    /**
     * 생일
     */
    @JsonProperty("birthDate")
    private LocalDateTime birthDate;
    /**
     * 프로필 이미지 url
     */
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;
    /**
     * 기념일
     */
    @JsonProperty("startedAt")
    private LocalDateTime startedAt;
}
