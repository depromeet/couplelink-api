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
    @Column(name = "connection_number", unique = true)
    private String connectionNumber;

    /**
     * 커플 정보
     */
    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    /**
     * 멤버 상세 정보
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
    private MemberDetail memberDetail;

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

    public Boolean isSolo() {
        return couple == null;
    }
}
