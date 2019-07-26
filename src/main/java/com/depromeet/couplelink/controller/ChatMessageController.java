package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.ChatMessageAssembler;
import com.depromeet.couplelink.dto.ChatMessageRequest;
import com.depromeet.couplelink.dto.ChatMessageResponse;
import com.depromeet.couplelink.entity.ChatMessage;
import com.depromeet.couplelink.model.IndexRange;
import com.depromeet.couplelink.service.ChatMessageFilterService;
import com.depromeet.couplelink.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageFilterService chatMessageFilterService;
    private final ChatMessageService chatMessageService;
    private final ChatMessageAssembler chatMessageAssembler;

    @GetMapping("/api/couples/{coupleId}/rooms/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageResponse> getMessages(@ApiIgnore @RequestAttribute Long memberId,
                                                 @RequestHeader("Authorization") String authorization,
                                                 @PathVariable Long coupleId,
                                                 @PathVariable Long roomId) {
        return Collections.emptyList();
    }

    @PostMapping("/api/couples/{coupleId}/rooms/{roomId}/messages")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMessageResponse createMessage(@ApiIgnore @RequestAttribute Long memberId,
                                             @RequestHeader("Authorization") String authorization,
                                             @PathVariable Long coupleId,
                                             @PathVariable Long roomId,
                                             @RequestBody @Valid ChatMessageRequest chatMessageRequest) {
        // TODO: HTTP 요청으로 채팅 메시지 보내면, 해당 채팅방에 전송되어야함.
        return new ChatMessageResponse();
    }

    @MessageMapping("/couples/{coupleId}/rooms/{roomId}/messages")
    @SendTo("/topic/couples/{coupleId}/rooms/{roomId}")
    @ResponseBody
    public ChatMessageResponse sendMessage(@DestinationVariable Long coupleId,
                                           @DestinationVariable Long roomId,
                                           @RequestBody @Valid ChatMessageRequest chatMessageRequest) {
        final ChatMessage chatMessage = chatMessageService.create(
                coupleId,
                roomId,
                chatMessageRequest.getMemberId(),
                chatMessageRequest.getMessage()
        );
        List<IndexRange> indexRanges = chatMessageFilterService.filter(
                coupleId,
                chatMessageRequest.getMemberId(),
                chatMessage.getId(),
                chatMessageRequest.getMessage()
        );
        return chatMessageAssembler.assembleChatMessageResponse(chatMessage, indexRanges);
    }
}
