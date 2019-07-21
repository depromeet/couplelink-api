package com.depromeet.couplelink.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {
    @NotNull
    private String message;
}
