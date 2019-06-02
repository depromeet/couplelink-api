package com.depromeet.couplelink.entity;

import com.depromeet.couplelink.model.ReadStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class FortuneCookieReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fortune_cookie_receipt_id")
    private Long id;

    /**
     * 쿠키 받는 사람 (= 읽는 사람)
     */
    @Column(name = "reader_member_id")
    private Long readerMemberId;

    /**
     * 커플 아이디 (반정규화)
     */
    @Column(name = "couple_id")
    private Long coupleId;

    /**
     * 읽음 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "read_status")
    private ReadStatus status;

    /**
     * 읽은 시간
     */
    @Column(name = "read_at")
    private LocalDateTime readAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 쿠키 정보
     */
    @OneToOne
    @JoinColumn(name = "fortune_cookie_id", nullable = false)
    private FortuneCookie fortuneCookie;
}
