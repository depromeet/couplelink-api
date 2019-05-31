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
public class FortuneCookie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fortune_cookie_id")
    private Long id;

    /**
     * 커플 아이디
     */
    @Column(name = "couple_id")
    private Long coupleId;

    /**
     * 쿠키 쓴 사람
     */
    @Column(name = "writer_member_id")
    private Long writerMemberId;

    /**
     * 내용
     */
    private String message;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
