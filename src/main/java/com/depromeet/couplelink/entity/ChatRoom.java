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
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_room_id")
    private Long id;

    /**
     * 커플 정보
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "chatRoom")
    private Couple couple;

    /**
     * 채팅방 이름
     */
    private String name;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
