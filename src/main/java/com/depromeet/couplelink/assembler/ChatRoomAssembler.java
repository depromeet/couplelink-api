package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.ChatRoomResponse;
import com.depromeet.couplelink.entity.ChatRoom;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ChatRoomAssembler {
    public ChatRoomResponse assembleChatRoomResponse(ChatRoom chatRoom) {
        Assert.notNull(chatRoom, "'chatRoom' must not be null");

        final ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
        chatRoomResponse.setId(chatRoom.getId());
        chatRoomResponse.setName(chatRoom.getName());

        return chatRoomResponse;
    }
}
