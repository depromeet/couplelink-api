package com.depromeet.couplelink.repository;

import com.depromeet.couplelink.entity.BannedTerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannedTermRepository extends JpaRepository<BannedTerm, Long> {
    Optional<BannedTerm> findByCoupleIdAndName(Long coupleId, String name);

    Optional<BannedTerm> findByIdAndCoupleId(Long termId, Long coupleId);

    @EntityGraph("bannedTermWithBannedTermLogs")
    Page<BannedTerm> findByCoupleId(Long coupleId, Pageable pageable);
}
