package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
     * 금지어 작성 시간
     */
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 금지어 어긴 횟수
     */
    @JsonProperty("count")
    private Integer count;
}
