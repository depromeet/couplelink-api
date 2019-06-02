package com.depromeet.couplelink.repository;

import com.depromeet.couplelink.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoupleRepository extends JpaRepository<Couple, Long> {
}
