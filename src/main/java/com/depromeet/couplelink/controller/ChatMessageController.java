package com.depromeet.couplelink.controller;

import com.depromeet.couplelink.dto.ChatMessageRequest;
import com.depromeet.couplelink.dto.ChatMessageResponse;
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
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Slf4j
public class ChatMessageController {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private static final Random RANDOM = new Random();

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
        chatMessageResponse.setWriter(createRandomMember());
        return chatMessageResponse;
    }

    private MemberResponse createRandomMember() {
        MemberResponse one = new MemberResponse();
        one.setId(1L);
        one.setName("여우");
        one.setCoupleId(0L);
        one.setConnectionNumber("123456");
        one.setMemberStatus(MemberStatus.COUPLE);
        one.setProfileImageUrl("profileImageUrl");

        MemberResponse theOther = new MemberResponse();
        theOther.setId(2L);
        theOther.setName("두루미");
        theOther.setCoupleId(0L);
        theOther.setConnectionNumber("654321");
        theOther.setMemberStatus(MemberStatus.COUPLE);
        theOther.setProfileImageUrl("profileImageUrl");

        return RANDOM.nextBoolean() ? one : theOther;
    }
}
