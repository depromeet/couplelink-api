package com.depromeet.couplelink.integration;

import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.BannedTermRequest;
import com.depromeet.couplelink.dto.BannedTermResponse;
import com.depromeet.couplelink.entity.BannedTerm;
import com.depromeet.couplelink.entity.BannedTermLog;
import com.depromeet.couplelink.helper.*;
import com.depromeet.couplelink.repository.BannedTermRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.depromeet.couplelink.helper.TestHelper.createBannedTermRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
public class BannedTermTest implements CoupleTestBase {
    private static final String BANNED_TERM_01 = "금지어1";
    private static final String BANNED_TERM_02 = "금지어2";
    private static final String BANNED_TERM_03 = "금지어3";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BannedTermRepository bannedTermRepository;

    @MockBean
    private KakaoAdapter mockKakaoAdapter;

    private LoginControllerApi loginControllerApi;
    private MemberControllerApi memberControllerApi;
    private CoupleControllerApi coupleControllerApi;
    private BannedTermControllerApi bannedTermControllerApi;

    private Long eveId;
    private String eveAccessToken;
    private Long adamId;
    private String adamAccessToken;
    private Long coupleId;

    @Before
    public void setup() throws Exception {
        loginControllerApi = new LoginControllerApi(mockMvc, objectMapper);
        memberControllerApi = new MemberControllerApi(mockMvc, objectMapper);
        coupleControllerApi = new CoupleControllerApi(mockMvc, objectMapper);
        bannedTermControllerApi = new BannedTermControllerApi(mockMvc, objectMapper);

        final CoupleValue coupleValue = this.createCouple(mockKakaoAdapter,
                loginControllerApi,
                memberControllerApi,
                coupleControllerApi);

        eveId = coupleValue.getEve().getId();
        eveAccessToken = coupleValue.getAccessTokenOfEve();
        adamId = coupleValue.getAdam().getId();
        adamAccessToken = coupleValue.getAccessTokenOfAdam();
        coupleId = coupleValue.getCouple().getId();
    }

    @Test
    public void 금지어_생성이_잘_되는지() throws Exception {
        // given
        final BannedTermRequest bannedTermRequest = createBannedTermRequest(BANNED_TERM_01);
        // when
        final TestApiResult<BannedTermResponse> createTermResult = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, bannedTermRequest);
        // then
        assertThat(createTermResult.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(createTermResult.getBody().getName()).isEqualTo(BANNED_TERM_01);
    }

    @Test
    public void 똑같은_금지어를_또_만드는_경우_이미_존재하는_금지어를_돌려주어야함() throws Exception {
        // given
        final BannedTermRequest bannedTermRequest = createBannedTermRequest(BANNED_TERM_01);
        final TestApiResult<BannedTermResponse> createTermResult1 = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, bannedTermRequest);
        assertThat(createTermResult1.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(createTermResult1.getBody().getName()).isEqualTo(BANNED_TERM_01);
        // when
        final TestApiResult<BannedTermResponse> createTermResult2 = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, bannedTermRequest);
        assertThat(createTermResult2.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(createTermResult1.getBody().getName()).isEqualTo(BANNED_TERM_01);
        // then
        assertThat(createTermResult1.getBody().getId()).isEqualTo(createTermResult2.getBody().getId());
    }

    @Test
    public void 금지어_목록이_잘_조회되는지() throws Exception {
        // given
        // 금지어 3개 생성
        final TestApiResult<BannedTermResponse> createTermResult1 = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, createBannedTermRequest(BANNED_TERM_01));
        assertThat(createTermResult1.getHttpStatus().is2xxSuccessful()).isTrue();
        final TestApiResult<BannedTermResponse> createTermResult2 = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, createBannedTermRequest(BANNED_TERM_02));
        assertThat(createTermResult2.getHttpStatus().is2xxSuccessful()).isTrue();
        final TestApiResult<BannedTermResponse> createTermResult3 = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, createBannedTermRequest(BANNED_TERM_03));
        assertThat(createTermResult3.getHttpStatus().is2xxSuccessful()).isTrue();
        // when
        final TestApiResult<List<BannedTermResponse>> getTermsResult = bannedTermControllerApi.getBannedTerms(eveAccessToken, coupleId, 0, 20);
        assertThat(getTermsResult.getHttpStatus().is2xxSuccessful()).isTrue();
        // then
        assertThat(getTermsResult.getBody().size()).isEqualTo(3);
    }

    @Test
    public void 금지어_어긴_횟수가_잘_나오는지() throws Exception {
        // 금지어 1개 생성
        final TestApiResult<BannedTermResponse> createTermResult = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, createBannedTermRequest(BANNED_TERM_01));
        assertThat(createTermResult.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(createTermResult.getBody().getCount()).isEqualTo(0);
        // 금지어 어긴 기록 생성
        final Long bannedTermId = createTermResult.getBody().getId();
        final BannedTerm bannedTerm = bannedTermRepository.findById(bannedTermId).orElse(null);
        List<BannedTermLog> bannedTermLogs = bannedTerm.getBannedTermLogs();
        final BannedTermLog log = new BannedTermLog();
        log.setCoupleId(coupleId);
        log.setBannedTerm(bannedTerm);
        log.setMemberId(adamId);
        log.setChatMessageId(10L);
        bannedTermLogs.add(log);
        bannedTermRepository.save(bannedTerm);
        // 금지어 조회 및 어긴횟수 확인
        final TestApiResult<BannedTermResponse> getTermResult = bannedTermControllerApi.getBannedTerm(eveAccessToken, coupleId, bannedTermId);
        assertThat(getTermResult.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(getTermResult.getBody().getCount()).isEqualTo(1);
    }

    @Test
    public void 금지어_삭제가_잘_되는지() throws Exception {
        // given
        final TestApiResult<BannedTermResponse> createTermResult = bannedTermControllerApi.createBannedTerm(eveAccessToken, coupleId, createBannedTermRequest(BANNED_TERM_01));
        assertThat(createTermResult.getHttpStatus().is2xxSuccessful()).isTrue();
        final Long bannedTermId = createTermResult.getBody().getId();
        // 금지어가 1개 존재하는지 확인
        final TestApiResult<List<BannedTermResponse>> getTermsResult1 = bannedTermControllerApi.getBannedTerms(eveAccessToken, coupleId, 0, 20);
        assertThat(getTermsResult1.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(getTermsResult1.getBody().size()).isEqualTo(1);
        // when
        final TestApiResult<Void> deleteTermResult = bannedTermControllerApi.deleteBannedTerm(eveAccessToken, coupleId, bannedTermId);
        assertThat(deleteTermResult.getHttpStatus()).isEqualTo(HttpStatus.NO_CONTENT);
        // then
        // 금지어가 0개 존재하는지 확인
        final TestApiResult<List<BannedTermResponse>> getTermsResult2 = bannedTermControllerApi.getBannedTerms(eveAccessToken, coupleId, 0, 20);
        assertThat(getTermsResult2.getHttpStatus().is2xxSuccessful()).isTrue();
        assertThat(getTermsResult2.getBody().size()).isEqualTo(0);
    }


}
