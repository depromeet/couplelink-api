package com.depromeet.couplelink.integration;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.CoupleRequest;
import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.dto.LoginResponse;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.helper.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
public class CreateCoupleTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KakaoAdapter kakaoAdapter;

    private LoginControllerApi loginControllerApi;
    private CoupleControllerApi coupleControllerApi;
    private MemberControllerApi memberControllerApi;

    @Before
    public void setup() {
        TestHelper.mockKakaoAdapter(kakaoAdapter, "kakaoToken1", 1L);
        TestHelper.mockKakaoAdapter(kakaoAdapter, "kakaoToken2", 2L);
        TestHelper.mockKakaoAdapter(kakaoAdapter, "kakaoToken3", 3L);

        loginControllerApi = new LoginControllerApi(mockMvc, objectMapper);
        coupleControllerApi = new CoupleControllerApi(mockMvc, objectMapper);
        memberControllerApi = new MemberControllerApi(mockMvc, objectMapper);
    }

    @Test
    public void 커플_생성_성공() throws Exception {
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
        final TestApiResult<MemberResponse> getInfoResult = memberControllerApi.getMe(accessToken2);
        assertThat(getInfoResult.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final Long memberId2 = getInfoResult.getBody().getId();

        // when
        final CoupleRequest coupleRequest = new CoupleRequest();
        ReflectionTestUtils.setField(coupleRequest, "memberId", memberId2);
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken1, coupleRequest);

        // then
        assertThat(createCoupleResult.getHttpStatus()).isEqualTo(HttpStatus.CREATED);
    }
}
