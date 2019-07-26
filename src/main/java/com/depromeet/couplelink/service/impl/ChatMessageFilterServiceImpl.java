package com.depromeet.couplelink.service.impl;

import com.depromeet.couplelink.entity.BannedTerm;
import com.depromeet.couplelink.entity.BannedTermLog;
import com.depromeet.couplelink.entity.Couple;
import com.depromeet.couplelink.model.IndexRange;
import com.depromeet.couplelink.repository.BannedTermLogRepository;
import com.depromeet.couplelink.repository.BannedTermRepository;
import com.depromeet.couplelink.service.ChatMessageFilterService;
import com.depromeet.couplelink.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageFilterServiceImpl implements ChatMessageFilterService {

    private final BannedTermRepository bannedTermRepository;
    private final BannedTermLogRepository bannedTermLogRepository;
    private final CoupleService coupleService;

    /**
     * 이 사용자를 기준으로 적용되어야할 금지어를 불러온다.
     * 입력받은 메시지에서 금지되어야 할 부분을 기록한다.
     *
     * @param writerMemberId 채팅 쓴사람
     * @param message        채팅 내용
     * @return 금지되어야할 부분
     */
    @Override
    @Transactional
    public List<IndexRange> filter(Long coupleId, Long writerMemberId, Long chatMessageId, String message) {
        if (writerMemberId == null) {
            return Collections.emptyList();
        }

        Couple couple = coupleService.getCouple(coupleId);
        Long bannedTermWriterMemberId = couple.getYourMemberId(writerMemberId);
        List<BannedTerm> bannedTerms = bannedTermRepository.findByCoupleIdAndWriterMemberId(coupleId, bannedTermWriterMemberId);

        List<IndexRange> indexRanges = new ArrayList<>();

        // TODO: 한 메시지에 한 금지어가 여러 번 나오는 경우 처리해야함. 지금은 1단어를 1번만 체크하고 넘어감
        String rest = message;
        for (BannedTerm bannedTerm : bannedTerms) {
            String bannedWord = bannedTerm.getName();
            int result = rest.indexOf(bannedWord);
            if (result < 0) {
                continue;
            }
            BannedTermLog bannedTermLog = new BannedTermLog();
            bannedTermLog.setCoupleId(coupleId);
            bannedTermLog.setBannedTerm(bannedTerm);
            bannedTermLog.setChatMessageId(chatMessageId);
            bannedTermLogRepository.save(bannedTermLog);

            IndexRange indexRange = new IndexRange();
            indexRange.setStartIndex(result);
            indexRange.setEndIndex(result + bannedWord.length());

            indexRanges.add(indexRange);
        }
        return indexRanges;
    }
}
