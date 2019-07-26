package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.ChatMessageResponse;
import com.depromeet.couplelink.entity.ChatMessage;
import com.depromeet.couplelink.model.IndexRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageAssembler {
    private final MemberAssembler memberAssembler;

    public ChatMessageResponse assembleChatMessageResponse(ChatMessage chatMessage, List<IndexRange> indexRanges) {
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setId(chatMessage.getId());
        chatMessageResponse.setWriter(memberAssembler.assembleMemberResponse(chatMessage.getWriterMember()));
        chatMessageResponse.setBannedIndexRange(indexRanges);
        chatMessageResponse.setCreatedAt(chatMessage.getCreatedAt());
        return chatMessageResponse;
    }
}
