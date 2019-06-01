package com.depromeet.couplelink.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_message_id")
    private Long id;

    /**
     * 커플 아이디 (반정규화)
     */
    @Column(name = "couple_id")
    private Long coupleId;

    /**
     * 채팅방 정보
     */
    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    /**
     * 메시지 보낸 사람
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 메시지 내용
     */
    private String message;

    /**
     * 메시지 생성 시간
     */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
