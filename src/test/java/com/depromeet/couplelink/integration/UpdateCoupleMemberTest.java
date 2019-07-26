package com.depromeet.couplelink.integration;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.dto.LoginResponse;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.dto.UpdateCoupleMemberRequest;
import com.depromeet.couplelink.helper.*;
import com.depromeet.couplelink.model.stereotype.ConnectionStatus;
import com.depromeet.couplelink.model.stereotype.GenderType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDateTime;

import static com.depromeet.couplelink.helper.TestHelper.createCoupleRequest;
import static com.depromeet.couplelink.helper.TestHelper.createUpdateCoupleMemberRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
@Ignore
public class UpdateCoupleMemberTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KakaoAdapter kakaoAdapter;

    private CoupleControllerApi coupleControllerApi;
    private MemberControllerApi memberControllerApi;
    private LoginControllerApi loginControllerApi;

    @Before
    public void setup() {
        TestHelper.mockKakaoAdapter(kakaoAdapter, "kakaoToken1", 1L);
        TestHelper.mockKakaoAdapter(kakaoAdapter, "kakaoToken2", 2L);

        coupleControllerApi = new CoupleControllerApi(mockMvc, objectMapper);
        memberControllerApi = new MemberControllerApi(mockMvc, objectMapper);
        loginControllerApi = new LoginControllerApi(mockMvc, objectMapper);
    }

    @Test
    public void 커플_연결하고나서_멤버_정보_입력이_잘_되는지() throws Exception {
        // given
        // 1번 유저 가입
        final TestApiResult<LoginResponse> loginResult1 = loginControllerApi.login(TestHelper.createLoginRequest("kakaoToken1"));
        assertThat(loginResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken1 = loginResult1.getBody().getAccessToken();
        // 2번 유저 가입
        final TestApiResult<LoginResponse> loginResult2 = loginControllerApi.login(TestHelper.createLoginRequest("kakaoToken2"));
        assertThat(loginResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken2 = loginResult2.getBody().getAccessToken();
        // 2번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult2 = memberControllerApi.getMe(accessToken2);
        assertThat(getInfoResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String connectionNumber = getInfoResult2.getBody().getConnectionNumber();
        // 1,2번 커플 생성
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken1, createCoupleRequest(connectionNumber));
        assertThat(createCoupleResult.getHttpStatus().is2xxSuccessful()).isTrue();
        final Long coupleId = createCoupleResult.getBody().getId();
        // when
        final UpdateCoupleMemberRequest request = createUpdateCoupleMemberRequest("티모", GenderType.MALE, "profileImageUrl", LocalDateTime.now(), LocalDateTime.now());
        final TestApiResult<CoupleResponse> updateCoupleMemberResult = coupleControllerApi.updateCoupleMember(accessToken1, coupleId, request);
        // then
        assertThat(updateCoupleMemberResult.getHttpStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    public void 커플_연결_후_멤버_정보_입력이_끝나면_연결_완료_상태로_변경되어야함() throws Exception {
        // given
        // given
        // 1번 유저 가입
        final TestApiResult<LoginResponse> loginResult1 = loginControllerApi.login(TestHelper.createLoginRequest("kakaoToken1"));
        assertThat(loginResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken1 = loginResult1.getBody().getAccessToken();
        // 2번 유저 가입
        final TestApiResult<LoginResponse> loginResult2 = loginControllerApi.login(TestHelper.createLoginRequest("kakaoToken2"));
        assertThat(loginResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken2 = loginResult2.getBody().getAccessToken();
        // 2번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult2 = memberControllerApi.getMe(accessToken2);
        assertThat(getInfoResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String connectionNumber = getInfoResult2.getBody().getConnectionNumber();
        // 1,2번 커플 생성
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken1, createCoupleRequest(connectionNumber));
        assertThat(createCoupleResult.getHttpStatus().is2xxSuccessful()).isTrue();
        final Long coupleId = createCoupleResult.getBody().getId();
        // when
        // 1번 유저 정보 입력
        final UpdateCoupleMemberRequest request1 = createUpdateCoupleMemberRequest("티모", GenderType.MALE, "profileImageUrl1", LocalDateTime.now(), LocalDateTime.now());
        final TestApiResult<CoupleResponse> updateCoupleMemberResult1 = coupleControllerApi.updateCoupleMember(accessToken1, coupleId, request1);
        assertThat(updateCoupleMemberResult1.getHttpStatus().is2xxSuccessful()).isTrue();
        // 2번 유저 정보 입력
        final UpdateCoupleMemberRequest request2 = createUpdateCoupleMemberRequest("트리스타나", GenderType.FEMALE, "profileImageUrl2", LocalDateTime.now(), LocalDateTime.now());
        final TestApiResult<CoupleResponse> updateCoupleMemberResult2 = coupleControllerApi.updateCoupleMember(accessToken2, coupleId, request2);
        assertThat(updateCoupleMemberResult2.getHttpStatus().is2xxSuccessful()).isTrue();
        // then
        assertThat(createCoupleResult.getBody().getConnectionStatus()).isEqualTo(ConnectionStatus.CONNECTING);
        assertThat(updateCoupleMemberResult1.getBody().getConnectionStatus()).isEqualTo(ConnectionStatus.CONNECTING);
        assertThat(updateCoupleMemberResult2.getBody().getConnectionStatus()).isEqualTo(ConnectionStatus.CONNECTED);
    }
}
