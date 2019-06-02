package com.depromeet.couplelink.entity;

import com.depromeet.couplelink.model.stereotype.ConnectionStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "couple")
    private ChatRoom chatRoom;

    /**
     * 가입 상태 (연결 중, 연결 완료)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "connection_status")
    private ConnectionStatus connectionStatus = ConnectionStatus.CONNECTING;

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

    public void updateConnectionStatus() {
        if (connectionStatus == ConnectionStatus.CONNECTED) {
            return;
        }
        long numberOfMemberDetails = members.stream()
                .map(Member::getMemberDetail)
                .filter(Objects::nonNull)
                .count();
        if (numberOfMemberDetails == 2L) {
            connectionStatus = ConnectionStatus.CONNECTED;
        }
    }

    public Long getYourMemberId(Long myMemberId) {
        Assert.notNull(myMemberId, "'myMemberId' must not be null");
        return members.stream()
                .map(Member::getId)
                .filter(id -> !myMemberId.equals(id))
                .findFirst()
                .orElse(null);
    }
}
