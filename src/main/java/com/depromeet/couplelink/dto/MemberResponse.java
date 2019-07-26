package com.depromeet.couplelink.dto;

import com.depromeet.couplelink.model.MemberStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponse {
    /**
     * 회원 아이디
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 회원 이름
     */
    @JsonProperty("name")
    private String name;

    /**
     * 회원 상태 (솔로인지 커플인지)
     */
    @JsonProperty("status")
    private MemberStatus memberStatus;

    /**
     * 연결할 때 사용하는 번호
     */
    @JsonProperty("connectionNumber")
    private String connectionNumber;

    /**
     * 프로필 이미지 url
     */
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;

    /**
     * 커플 아이디, 솔로이면 null
     */
    @JsonProperty("coupleId")
    private Long coupleId;

    /**
     * 생일
     */
    @JsonProperty("birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthDate;
}
