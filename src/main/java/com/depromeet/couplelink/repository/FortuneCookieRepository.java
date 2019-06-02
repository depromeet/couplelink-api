package com.depromeet.couplelink.repository;

import com.depromeet.couplelink.entity.FortuneCookie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FortuneCookieRepository extends JpaRepository<FortuneCookie, Long> {
    Optional<FortuneCookie> findByIdAndCoupleId(Long cookieId, Long coupleId);

    Page<FortuneCookie> findByCoupleIdAndWriterMemberIdIn(Long coupleId, List<Long> writerMemberIds, Pageable pageable);
}
