package com.depromeet.couplelink.integration;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.LoginRequest;
import com.depromeet.couplelink.dto.LoginResponse;
import com.depromeet.couplelink.entity.Member;
import com.depromeet.couplelink.entity.ProviderType;
import com.depromeet.couplelink.helper.LoginControllerApi;
import com.depromeet.couplelink.helper.TestApiResult;
import com.depromeet.couplelink.helper.TestHelper;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
public class LoginTest {
    private static final String KAKAO_ACCESS_TOKEN = "123123";
    private static final Long KAKAO_USER_ID = 10L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private KakaoAdapter kakaoAdapter;

    private LoginControllerApi loginControllerApi;

    @Before
    public void setup() {
        TestHelper.mockKakaoAdapter(kakaoAdapter, KAKAO_ACCESS_TOKEN, KAKAO_USER_ID);
        loginControllerApi = new LoginControllerApi(mockMvc, objectMapper);
    }

    @Test
    public void 처음_로그인하는_경우_멤버가_생성되어야함() throws Exception {
        // given
        final LoginRequest loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(loginRequest, "accessToken", KAKAO_ACCESS_TOKEN);
        // when
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginRequest)))
                // then 1
                .andExpect(status().isOk());
        // then 2
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void 이미_가입한_멤버가_로그인하는_경우_멤버가_새로_생성되지_않아야함() throws Exception {
        // given
        Member member = new Member();
        member.setProviderType(ProviderType.KAKAO);
        member.setProviderUserId(KAKAO_USER_ID.toString());
        member.setConnectionNumber(UUID.randomUUID().toString());
        memberRepository.save(member);
        assertThat(memberRepository.findAll().size()).isEqualTo(1);

        final LoginRequest loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(loginRequest, "accessToken", KAKAO_ACCESS_TOKEN);
        // when
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginRequest)))
                // then 1
                .andExpect(status().isOk());
        // then 2
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void 로그인하고_받은_토큰으로_api_요청하면_401_응답이_나오지_않아야함() throws Exception {
        // given
        final LoginRequest loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(loginRequest, "accessToken", KAKAO_ACCESS_TOKEN);

        final TestApiResult<LoginResponse> loginResult = loginControllerApi.login(loginRequest);
        assertThat(loginResult.getHttpStatus()).isEqualTo(HttpStatus.OK);
        // when
        mockMvc.perform(get("/api/members/me")
                .header("Authorization", "Bearer " + loginResult.getBody().getAccessToken()))
                // then
                .andExpect(status().isOk());
    }
}
