package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.dto.MemberResponse;
import lombok.Value;

@Value(staticConstructor = "of")
public class CoupleValue {
    /**
     * 여자 멤버
     */
    private final MemberResponse eve;
    /**
     * 여자 accessToken
     */
    private final String accessTokenOfEve;
    /**
     * 남자 멤버
     */
    private final MemberResponse adam;
    /**
     * 남자 accessToken
     */
    private final String accessTokenOfAdam;
    /**
     * 커플 정보
     */
    private final CoupleResponse couple;
}
