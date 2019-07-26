package com.depromeet.couplelink.repository;

import com.depromeet.couplelink.entity.Couple;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {
    @EntityGraph("coupleWithMembers")
    Optional<Couple> findById(Long coupleId);
}
