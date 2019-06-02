package com.depromeet.couplelink.assembler;

import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.entity.Couple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoupleAssembler {
    private final MemberAssembler memberAssembler;
    private final ChatRoomAssembler chatRoomAssembler;

    public CoupleResponse assembleCoupleResponse(Couple couple) {
        Assert.notNull(couple, "'couple' must not be null");

        final CoupleResponse coupleResponse = new CoupleResponse();
        coupleResponse.setId(couple.getId());
        coupleResponse.setMembers(couple.getMembers()
                .stream()
                .map(memberAssembler::assembleMemberResponse)
                .collect(Collectors.toList()));
        coupleResponse.setChatRoomResponse(chatRoomAssembler.assembleChatRoomResponse(couple.getChatRoom()));
        coupleResponse.setConnectionStatus(couple.getConnectionStatus());
        coupleResponse.setStartedAt(couple.getStartedAt());
        return coupleResponse;
    }
}
