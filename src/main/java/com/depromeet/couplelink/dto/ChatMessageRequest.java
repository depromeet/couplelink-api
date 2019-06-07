package com.depromeet.couplelink.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class ChatMessageRequest {
    @NotNull
    private String message;
}
