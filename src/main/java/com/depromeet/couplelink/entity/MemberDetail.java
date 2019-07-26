package com.depromeet.couplelink.entity;

import com.depromeet.couplelink.model.stereotype.GenderType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class MemberDetail {
    private static final DateTimeFormatter BIRTH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_detail_id")
    private Long id;

    /**
     * 이름
     */
    private String name;

    /**
     * 성별
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_type")
    private GenderType genderType;

    /**
     * 생일
     */
    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    /**
     * 프로필 이미지 url
     */
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    /**
     * 멤버 정보
     */
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void parseBirthDate(String birthDate) {
        this.birthDate = LocalDateTime.from(BIRTH_DATE_FORMATTER.parse(birthDate));
    }
}
