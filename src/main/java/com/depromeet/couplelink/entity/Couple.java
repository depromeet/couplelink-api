package com.depromeet.couplelink.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "coupleWithMembers", attributeNodes = @NamedAttributeNode("members"))
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "couple_id")
    private Long id;

    /**
     * 커플을 구성하는 회원 (2명이어야함)
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "couple")
    private List<Member> members = new ArrayList<>();

    /**
     * 채팅방 아이디
     */
    @OneToOne
    @JoinColumn(name = "chat_room_id", nullable = true)
    private ChatRoom chatRoom;

    /**
     * 기념일 (만나기 시작한 날)
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
