package com.depromeet.couplelink.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

public class AbstractControllerApi {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    protected AbstractControllerApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    protected HttpHeaders getAuthorizationHeader(String accessToken) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        return httpHeaders;
    }
}
