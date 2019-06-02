package com.depromeet.couplelink.repository;

import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.entity.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderTypeAndProviderUserId(ProviderType providerType, String providerUserId);

    Optional<Member> findByConnectionNumber(String connectionNumber);
}
