package com.depromeet.couplelink.helper;

import com.depromeet.couplelink.dto.LoginRequest;
import com.depromeet.couplelink.dto.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class LoginControllerApi extends AbstractControllerApi {
    public LoginControllerApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public TestApiResult<LoginResponse> login(LoginRequest loginRequest) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();
        return new TestApiResult(mvcResult, objectMapper, LoginResponse.class);
    }
}
