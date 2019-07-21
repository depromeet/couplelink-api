package com.depromeet.couplelink.adapter;

import com.depromeet.couplelink.dto.ChatMessageRequest;
import com.depromeet.couplelink.dto.ChatMessageResponse;

public interface WebSocketAdapter {
    ChatMessageResponse send(Long coupleId, Long roomId, ChatMessageRequest chatMessageRequest);
    void sendByClient(Long coupleId, Long roomId, ChatMessageRequest chatMessageRequest);
}
