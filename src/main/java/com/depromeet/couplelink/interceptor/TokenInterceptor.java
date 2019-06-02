package com.depromeet.couplelink.interceptor;

import com.depromeet.couplelink.component.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("tokenInterceptor")
@RequiredArgsConstructor
public class TokenInterceptor extends HandlerInterceptorAdapter {
    private final JwtFactory jwtFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long memberId = jwtFactory.decodeToken(request.getHeader("Authorization")).orElse(null);
        if (memberId == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        request.setAttribute("memberId", memberId);
        return true;
    }
}
