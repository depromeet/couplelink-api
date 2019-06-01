package com.depromeet.couplelink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatRoomResponse {
    /**
     * 채팅방 아이디
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 채팅방 이름
     */
    @JsonProperty("name")
    private String name;
}
