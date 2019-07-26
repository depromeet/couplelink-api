package com.depromeet.couplelink.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "bannedTermWithBannedTermLogs", attributeNodes = @NamedAttributeNode("bannedTermLogs"))
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class BannedTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "banned_term_id")
    private Long id;

    /**
     * 커플 아이디 (반정규화)
     */
    @Column(name = "couple_id")
    private Long coupleId;

    /**
     * 금지어 등록한 사람
     */
    @Column(name = "writer_member_id")
    private Long writerMemberId;

    /**
     * 금지어 내용
     */
    private String name;

    /**
     * 삭제 여부
     */
    private Boolean deleted;

    @OneToMany(mappedBy = "bannedTerm")
    private List<BannedTermLog> bannedTermLogs = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Boolean isNotDeleted() {
        return !deleted;
    }
}
