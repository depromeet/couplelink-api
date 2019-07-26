package com.depromeet.couplelink.service;

import com.depromeet.couplelink.entity.ChatMessage;

public interface ChatMessageService {
    ChatMessage create(Long coupleId, Long roomId, Long writerId, String message);
}
