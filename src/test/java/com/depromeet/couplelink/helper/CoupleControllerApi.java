package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.dto.CoupleRequest;
import com.depromeet.couplelink.dto.CoupleResponse;
import com.depromeet.couplelink.dto.UpdateCoupleMemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public final class CoupleControllerApi extends AbstractControllerApi {
    public CoupleControllerApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public TestApiResult<CoupleResponse> createCouple(String accessToken, CoupleRequest coupleRequest) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/api/couples")
                .headers(this.getAuthorizationHeader(accessToken))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(coupleRequest)))
                .andReturn();

        return new TestApiResult<>(mvcResult, objectMapper, CoupleResponse.class);
    }

    public TestApiResult<CoupleResponse> updateCoupleMember(String accessToken, Long coupleId, UpdateCoupleMemberRequest request) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(put("/api/couples/{coupleId}/members/me", coupleId)
                .headers(this.getAuthorizationHeader(accessToken))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        return new TestApiResult<>(mvcResult, objectMapper, CoupleResponse.class);
    }
}
