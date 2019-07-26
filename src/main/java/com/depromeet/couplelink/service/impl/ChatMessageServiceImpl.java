package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.entity.ChatMessage;
import com.depromeet.couplelink.exception.ApiFailedException;
import com.depromeet.couplelink.repository.ChatMessageRepository;
import com.depromeet.couplelink.repository.ChatRoomRepository;
import com.depromeet.couplelink.service.ChatMessageService;
import com.depromeet.couplelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    @Override
    @Transactional
    public ChatMessage create(Long coupleId, Long roomId, Long writerId, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setCoupleId(coupleId);
        chatMessage.setChatRoom(chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ApiFailedException("ChatRoom not found.", HttpStatus.NOT_FOUND)));
        chatMessage.setMessage(message);
        chatMessage.setWriterMember(memberService.getMemberById(writerId));
        return chatMessageRepository.save(chatMessage);
    }
}
