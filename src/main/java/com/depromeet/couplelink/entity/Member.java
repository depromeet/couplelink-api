package com.depromeet.couplelink.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    /**
     * 회원 이름
     */
    private String name;

    /**
     * 프로필 이미지 url
     */
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    /**
     * 인증 제공자 정보
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider_id")
    private ProviderType providerType;

    /**
     * 인증 제공자에서 주는 아이디
     */
    @Column(name = "provider_user_id")
    private String providerUserId;

    /**
     * 커플로 상대방을 연결할 때 사용하는 번호
     */
    @Column(name = "connection_number")
    private String connectionNumber;

    /**
     * 커플 정보
     */
    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Optional<Long> getCoupleId() {
        return Optional.ofNullable(couple)
                .map(Couple::getId);
    }
}
