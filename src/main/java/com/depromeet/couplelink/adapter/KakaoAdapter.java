package com.depromeet.couplelink.adapter;

import com.depromeet.couplelink.dto.kakao.KakaoUserResponse;

public interface KakaoAdapter {
    KakaoUserResponse getUserInfo(String accessToken);
}
