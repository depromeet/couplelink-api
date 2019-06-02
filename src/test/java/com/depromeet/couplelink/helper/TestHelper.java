package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.LoginRequest;
import com.depromeet.couplelink.dto.kakao.KakaoUserResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TestHelper {
    private TestHelper() {

    }

    /**
     * 카카오 로그인에 사용되는 데이터를 mocking 합니다
     *
     * @param mockKakaoAdapter mocking 된 kakaoAdapter
     * @param kakaoAccessToken 입력할 카카오 토큰
     * @param kakaoUserId      토큰에서 추출하고싶은 아이디
     */
    public static void mockKakaoAdapter(KakaoAdapter mockKakaoAdapter,
                                        String kakaoAccessToken,
                                        Long kakaoUserId) {
        final KakaoUserResponse mockKakaoUserResponse = mock(KakaoUserResponse.class);
        when(mockKakaoUserResponse.getId()).thenReturn(kakaoUserId);
        when(mockKakaoUserResponse.getProfileImage()).thenReturn("profileImageUrl");
        when(mockKakaoUserResponse.getUserName()).thenReturn("name");

        when(mockKakaoAdapter.getUserInfo(kakaoAccessToken)).thenReturn(mockKakaoUserResponse);
    }

    public static LoginRequest createLoginRequest(String accessToken) {
        final LoginRequest loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(loginRequest, "accessToken", accessToken);
        return loginRequest;
    }
}
