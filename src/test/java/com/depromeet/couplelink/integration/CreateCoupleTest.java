package com.depromeet.couplelink.integration;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.CoupleRequest;
import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.dto.LoginResponse;
import com.depromeet.couplelink.dto.MemberResponse;
import com.depromeet.couplelink.helper.*;
import com.depromeet.couplelink.repository.MemberRepository;
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

import javax.persistence.EntityManager;

import static com.depromeet.couplelink.helper.TestHelper.createCoupleRequest;
import static com.depromeet.couplelink.helper.TestHelper.createLoginRequest;
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
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

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
        final TestApiResult<LoginResponse> loginResult1 = loginControllerApi.login(createLoginRequest("kakaoToken1"));
        assertThat(loginResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken1 = loginResult1.getBody().getAccessToken();
        // 2번 유저 가입
        final TestApiResult<LoginResponse> loginResult2 = loginControllerApi.login(createLoginRequest("kakaoToken2"));
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

    @Test
    public void 연결하려는_멤버_중_내가_커플인경우_커플_생성_실패() throws Exception {
        // given
        // 1번 유저 가입
        final TestApiResult<LoginResponse> loginResult1 = loginControllerApi.login(createLoginRequest("kakaoToken1"));
        assertThat(loginResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken1 = loginResult1.getBody().getAccessToken();
        // 2번 유저 가입
        final TestApiResult<LoginResponse> loginResult2 = loginControllerApi.login(createLoginRequest("kakaoToken2"));
        assertThat(loginResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken2 = loginResult2.getBody().getAccessToken();
        // 2번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult2 = memberControllerApi.getMe(accessToken2);
        assertThat(getInfoResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final Long memberId2 = getInfoResult2.getBody().getId();
        // 1번, 2번 커플 생성
        커플_생성(accessToken1, memberId2);

        // 3번 유저 가입
        final TestApiResult<LoginResponse> loginResult3 = loginControllerApi.login(createLoginRequest("kakaoToken3"));
        assertThat(loginResult3.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken3 = loginResult3.getBody().getAccessToken();
        // 3번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult3 = memberControllerApi.getMe(accessToken3);
        assertThat(getInfoResult3.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final Long memberId3 = getInfoResult3.getBody().getId();

        // when
        // 1번, 3번 커플 생성 요청
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken1, createCoupleRequest(memberId3));
        // then
        assertThat(createCoupleResult.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private void 커플_생성(String accessToken, Long memberId) throws Exception {
        final CoupleRequest coupleRequest = createCoupleRequest(memberId);
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken, coupleRequest);
        assertThat(createCoupleResult.getHttpStatus()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void 연결하려는_멤버_중_상대가_커플인경우_커플_생성_실패() throws Exception {
        // given
        // 1번 유저 가입
        final TestApiResult<LoginResponse> loginResult1 = loginControllerApi.login(createLoginRequest("kakaoToken1"));
        assertThat(loginResult1.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken1 = loginResult1.getBody().getAccessToken();
        // 2번 유저 가입
        final TestApiResult<LoginResponse> loginResult2 = loginControllerApi.login(createLoginRequest("kakaoToken2"));
        assertThat(loginResult2.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken2 = loginResult2.getBody().getAccessToken();
        // 3번 유저 가입
        final TestApiResult<LoginResponse> loginResult3 = loginControllerApi.login(createLoginRequest("kakaoToken3"));
        assertThat(loginResult3.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final String accessToken3 = loginResult3.getBody().getAccessToken();
        // 3번 유저 정보 조회
        final TestApiResult<MemberResponse> getInfoResult3 = memberControllerApi.getMe(accessToken3);
        assertThat(getInfoResult3.getHttpStatus()).isEqualTo(HttpStatus.OK);
        final Long memberId3 = getInfoResult3.getBody().getId();
        // 2,3번 커플 생성
        커플_생성(accessToken2, memberId3);
        // when
        // 1,3번 커플 생성 요청
        final TestApiResult<CoupleResponse> createCoupleResult = coupleControllerApi.createCouple(accessToken1, createCoupleRequest(memberId3));
        // then
        assertThat(createCoupleResult.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
