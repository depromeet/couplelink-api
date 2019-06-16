package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.dto.LoginResponse;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.dto.UpdateCoupleMemberRequest;
import com.depromeet.couplelink.model.stereotype.GenderType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.depromeet.couplelink.helper.TestHelper.createCoupleRequest;
import static com.depromeet.couplelink.helper.TestHelper.createUpdateCoupleMemberRequest;
import static org.assertj.core.api.Assertions.assertThat;

public interface CoupleTestBase {

    default CoupleValue createCouple(KakaoAdapter mockKakaoAdapter,
                                     LoginControllerApi loginControllerApi,
                                     MemberControllerApi memberControllerApi,
                                     CoupleControllerApi coupleControllerApi) throws Exception {
        final String kakaoToken1 = "kakaoToken1";
        final String kakaoToken2 = "kakaoToken2";

        TestHelper.mockKakaoAdapter(mockKakaoAdapter, kakaoToken1, 1L);
        TestHelper.mockKakaoAdapter(mockKakaoAdapter, kakaoToken2, 2L);

        // 1번 유저 가입
        final TestApiResult<LoginResponse> loginResult1 = loginControllerApi.login(TestHelper.createLoginRequest(kakaoToken1));
        assertThat(loginResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken1 = loginResult1.getBody().getAccessToken();
        // 2번 유저 가입
        final TestApiResult<LoginResponse> loginResult2 = loginControllerApi.login(TestHelper.createLoginRequest(kakaoToken2));
        assertThat(loginResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken2 = loginResult2.getBody().getAccessToken();
        // 1번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult1 = memberControllerApi.getMe(accessToken1);
        assertThat(getInfoResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final MemberResponse memberResponse1 = getInfoResult1.getBody();
        // 2번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult2 = memberControllerApi.getMe(accessToken2);
        assertThat(getInfoResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final MemberResponse memberResponse2 = getInfoResult2.getBody();
        // 1,2번 커플 생성
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken1, createCoupleRequest(memberResponse2.getConnectionNumber()));
        assertThat(createCoupleResult.getHttpStatus().is2xxSuccessful()).isTrue();
        final CoupleResponse coupleResponse = createCoupleResult.getBody();
        // 1번 유저 정보 입력
        final UpdateCoupleMemberRequest request1 = createUpdateCoupleMemberRequest("eve", GenderType.FEMALE, "profileImageUrl1", LocalDateTime.now(), LocalDateTime.now());
        final TestApiResult<CoupleResponse> updateCoupleMemberResult1 = coupleControllerApi.updateCoupleMember(accessToken1, coupleResponse.getId(), request1);
        assertThat(updateCoupleMemberResult1.getHttpStatus().is2xxSuccessful()).isTrue();
        // 2번 유저 정보 입력
        final UpdateCoupleMemberRequest request2 = createUpdateCoupleMemberRequest("adam", GenderType.MALE, "profileImageUrl2", LocalDateTime.now(), LocalDateTime.now());
        final TestApiResult<CoupleResponse> updateCoupleMemberResult2 = coupleControllerApi.updateCoupleMember(accessToken2, coupleResponse.getId(), request2);
        assertThat(updateCoupleMemberResult2.getHttpStatus().is2xxSuccessful()).isTrue();

        return CoupleValue.of(memberResponse1, accessToken1, memberResponse2, accessToken2, coupleResponse);
    }
}
