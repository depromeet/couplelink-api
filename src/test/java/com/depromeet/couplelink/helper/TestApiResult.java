package com.depromeet.couplelink.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

@Value
public class TestApiResult<T> {
    private HttpStatus httpStatus;
    private T body;

    public TestApiResult(MvcResult mvcResult, ObjectMapper objectMapper, Class<T> clazz) throws IOException {
        this.httpStatus = HttpStatus.valueOf(mvcResult.getResponse().getStatus());
        this.body = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), clazz);
    }
}
