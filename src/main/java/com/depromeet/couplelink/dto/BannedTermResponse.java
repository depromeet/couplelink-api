package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BannedTermResponse {
    /**
     * 금지어 아이디
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 금지어
     */
    @JsonProperty("name")
    private String name;

    /**
     * 금지어 작성자
     */
    @JsonProperty("writer")
    private MemberResponse writerMemberResponse;

    /**
     * 금지어 지킬 사람
     */
    @JsonProperty("receiver")
    private MemberResponse receiverMemberResponse;

    /**
     * 금지어 작성 시간
     */
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
