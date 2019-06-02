package com.depromeet.couplelink.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class KakaoUserResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("properties")
    private Map<String, String> properties;

    @JsonProperty("kakao_account")
    private Map<String, Object> account;

    public String getUserName() {
        return this.properties.get("nickname");
    }

    public String getProfileImage() {
        return this.properties.get("profile_image");
    }
}
