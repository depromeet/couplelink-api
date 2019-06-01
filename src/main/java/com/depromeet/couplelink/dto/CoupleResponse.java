package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class CoupleResponse {
    /**
     * 커플 아이디
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 커플을 구성하는 멤버들 (2명)
     */
    @JsonProperty("members")
    private List<MemberResponse> members;

    /**
     * 기념일 (사귄날)
     */
    @JsonProperty("startedAt")
    private LocalDateTime startedAt;

    /**
     * 커플 채팅방 정보
     */
    @JsonProperty("chatRoom")
    private ChatRoomResponse chatRoomResponse;
}
