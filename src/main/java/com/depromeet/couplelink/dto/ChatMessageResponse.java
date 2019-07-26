package com.depromeet.couplelink.dto;

import com.depromeet.couplelink.model.IndexRange;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatMessageResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("writer")
    private MemberResponse writer;

    @JsonProperty("message")
    private String message;

    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("bannedIndexRange")
    private List<IndexRange> bannedIndexRange;
}
