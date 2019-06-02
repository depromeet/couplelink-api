package com.depromeet.couplelink.adapter.impl;

import com.depromeet.couplelink.exception.ApiFailedException;
import com.depromeet.couplelink.adapter.KakaoAdapter;
import com.depromeet.couplelink.dto.kakao.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class KakaoAdapterImpl implements KakaoAdapter {
    private final RestTemplate restTemplate;

    @Override
    public KakaoUserResponse getUserInfo(String accessToken) {
        final URI requestUrl = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me")
                .build(true)
                .toUri();

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        final HttpEntity httpEntity = new HttpEntity(httpHeaders);

        final ResponseEntity<KakaoUserResponse> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, KakaoUserResponse.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ApiFailedException("Failed to get User details from kakao api", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity.getBody();
    }
}
