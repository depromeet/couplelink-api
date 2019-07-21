package com.depromeet.couplelink.adapter.impl;

import com.depromeet.couplelink.adapter.WebSocketAdapter;
import com.depromeet.couplelink.dto.ChatMessageRequest;
import com.depromeet.couplelink.dto.ChatMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class WebSocketAdapterImpl implements WebSocketAdapter {

    private WebSocketStompClient stompClient;

    @PostConstruct
    public void init() {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);

        this.stompClient.setMessageConverter(converter);
    }

    @Override
    @SendTo("/topic/couples/{coupleId}/rooms/{roomId}")
    public ChatMessageResponse send(@DestinationVariable Long coupleId,
                                    @DestinationVariable Long roomId,
                                    ChatMessageRequest chatMessageRequest) {

        log.error("coupleId: {}, roomId: {}, chatMessageRequest: {}",
                coupleId,
                roomId,
                chatMessageRequest
        );
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setMessage("Hello world");

        log.error("{}", chatMessageResponse);
        return chatMessageResponse;
    }

    @Override
    public void sendByClient(Long coupleId, Long roomId, ChatMessageRequest chatMessageRequest) {

        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();

        final StompSessionHandler handler = new TestSessionHandler(failure, chatMessageRequest.getMessage()) {
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/couples/" + coupleId + "/rooms/" + roomId, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return ChatMessageResponse.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        final ChatMessageResponse chatMessageResponse = (ChatMessageResponse) payload;
                        try {
                            log.error(chatMessageResponse.getMessage());
                        } catch (Throwable t) {
                            failure.set(t);
                        } finally {
                            session.disconnect();
                            latch.countDown();
                        }
                    }
                });
                try {
                    final ChatMessageRequest chatMessageRequest = new ChatMessageRequest(this.getMessage());
                    session.send("/app/couples/" + coupleId + "/rooms/" + roomId + "/messages", chatMessageRequest);
                } catch (Throwable t) {
                    failure.set(t);
                    latch.countDown();
                }
            }
        };

        this.stompClient.connect("ws://localhost:8080/api/websocket", handler);
    }

    private static class TestSessionHandler extends StompSessionHandlerAdapter {

        private final AtomicReference<Throwable> failure;
        private final String message;

        public TestSessionHandler(AtomicReference<Throwable> failure, String message) {
            this.failure = failure;
            this.message = message;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            this.failure.set(new Exception(headers.toString()));
        }

        @Override
        public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
            this.failure.set(ex);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable ex) {
            this.failure.set(ex);
        }


        public String getMessage() {
            return message;
        }
    }

}
