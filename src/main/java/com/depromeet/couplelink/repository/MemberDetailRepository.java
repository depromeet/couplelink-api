package com.depromeet.couplelink.repository;

import com.depromeet.couplelink.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {
    Optional<MemberDetail> findByMemberId(Long memberId);
}
