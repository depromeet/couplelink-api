package com.depromeet.couplelink.service;

import com.depromeet.couplelink.model.IndexRange;

import java.util.List;

public interface ChatMessageFilterService {
    List<IndexRange> filter(Long coupleId, Long writerId, String message);
}
