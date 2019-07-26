package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.repository.MemberRepository;
import com.depromeet.couplelink.service.ConnectionNumberCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConnectionNumberCreationServiceImpl implements ConnectionNumberCreationService {

    private static final Random RANDOM = new Random();

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public String create() {
        String connectionNumber = this.createConnectionNumber();
        while (memberRepository.findByConnectionNumber(connectionNumber).isPresent()) {
            connectionNumber = this.createConnectionNumber();
        }
        return connectionNumber;
    }

    private String createConnectionNumber() {
        return String.format("%d-%d", createNumber(), createNumber());
    }

    private int createNumber() {
        return RANDOM.nextInt(9000) + 1000;
    }
}
