package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.dto.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public final class MemberControllerApi extends AbstractControllerApi {

    public MemberControllerApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public TestApiResult<MemberResponse> getMe(String accessToken) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/members/me")
                .headers(this.getAuthorizationHeader(accessToken)))
                .andDo(print())
                .andReturn();
        return new TestApiResult<>(mvcResult, objectMapper, MemberResponse.class);
    }
}
