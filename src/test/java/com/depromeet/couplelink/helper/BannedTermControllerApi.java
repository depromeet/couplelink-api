package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.dto.BannedTermRequest;
import com.depromeet.couplelink.dto.BannedTermResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class BannedTermControllerApi extends AbstractControllerApi {
    private static final TypeReference<List<BannedTermResponse>> TYPE_REFERENCE_LIST_BANNED_TERM_RESPONSE = new TypeReference<List<BannedTermResponse>>() {
    };

    public BannedTermControllerApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public TestApiResult<List<BannedTermResponse>> getBannedTerms(String accessToken, Long coupleId, Integer page, Integer size) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/couples/{coupleId}/banned-terms", coupleId)
                .headers(this.getAuthorizationHeader(accessToken))
                .param("page", page.toString())
                .param("size", size.toString()))
                .andReturn();
        return new TestApiResult<>(mvcResult, objectMapper, TYPE_REFERENCE_LIST_BANNED_TERM_RESPONSE);
    }

    public TestApiResult<BannedTermResponse> getBannedTerm(String accessToken, Long coupleId, Long termId) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/couples/{coupleId}/banned-terms/{bannedTermId}", coupleId, termId)
                .headers(this.getAuthorizationHeader(accessToken)))
                .andReturn();
        return new TestApiResult(mvcResult, objectMapper, BannedTermResponse.class);
    }

    public TestApiResult<BannedTermResponse> createBannedTerm(String accessToken, Long coupleId, BannedTermRequest bannedTermRequest) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/api/couples/{coupleId}/banned-terms", coupleId)
                .headers(this.getAuthorizationHeader(accessToken))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(bannedTermRequest)))
                .andReturn();
        return new TestApiResult(mvcResult, objectMapper, BannedTermResponse.class);
    }

    public TestApiResult<Void> deleteBannedTerm(String accessToken, Long coupleId, Long bannedTermId) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(delete("/api/couples/{coupleId}/banned-terms/{bannedTermId}", coupleId, bannedTermId)
                .headers(this.getAuthorizationHeader(accessToken)))
                .andReturn();
        return new TestApiResult(mvcResult, objectMapper, Void.class);
    }

}
