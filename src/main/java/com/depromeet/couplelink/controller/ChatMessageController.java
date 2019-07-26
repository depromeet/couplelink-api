package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.assembler.MemberAssembler;
import com.depromeet.couplelink.dto.ChatMessageRequest;
import com.depromeet.couplelink.dto.ChatMessageResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.model.IndexRange;
import com.depromeet.couplelink.service.ChatMessageFilterService;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.model.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private static final Random RANDOM = new Random();

    private final ChatMessageFilterService chatMessageFilterService;
    private final MemberService memberService;
    private final MemberAssembler memberAssembler;

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
        // FIXME: 웹소켓 통신 테스트를 위한 임시 구현이므로 수정 필요.
        final ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setId(ATOMIC_INTEGER.incrementAndGet());
        chatMessageResponse.setMessage(chatMessageRequest.getMessage());
        chatMessageResponse.setCreatedAt(LocalDateTime.now());

        Member member = memberService.getMemberById(chatMessageRequest.getMemberId());
        chatMessageResponse.setWriter(memberAssembler.assembleMemberResponse(member));

        List<IndexRange> indexRanges = chatMessageFilterService.filter(coupleId, chatMessageRequest.getMemberId(), chatMessageRequest.getMessage());
        chatMessageResponse.setBannedIndexRange(indexRanges);
        return chatMessageResponse;
    }
}
