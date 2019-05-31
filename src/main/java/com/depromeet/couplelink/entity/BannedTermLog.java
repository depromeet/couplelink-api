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
public class BannedTermLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "banned_term_log_id")
    private Long id;

    /**
     * 커플 아이디 (반정규화)
     */
    @Column(name = "couple_id")
    private Long coupleId;

    /**
     * 금지어 정보
     */
    @ManyToOne
    @JoinColumn(name = "banned_term_id")
    private BannedTerm bannedTerm;

    /**
     * 금지어 말한 사람
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 금지어가 포함된 채팅 메시지의 아이디
     */
    @Column(name = "chat_message_id")
    private Long chatMessageId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
