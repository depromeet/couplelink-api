package com.depromeet.couplelink.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private Integer id;

    private MemberResponse writer;

    private String message;

    private LocalDateTime createdAt;
}
