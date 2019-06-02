package com.depromeet.couplelink.config;

import com.depromeet.couplelink.component.JwtFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {

    @Value("${jwt.tokenIssuer:defaultTokenIssuer}")
    private String tokenIssuer;
    @Value("${jwt.tokenSigningKey:defaultTokenSigningKey}")
    private String tokenSigningKey;

    @Bean
    public JwtFactory jwtFactory() {
        return new JwtFactory(tokenIssuer, tokenSigningKey);
    }
}
