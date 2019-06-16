package com.depromeet.couplelink.aop;

import com.depromeet.couplelink.exception.ApiFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("MethodArgumentNotValidException", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(ApiFailedException.class)
    public String handleApiFailedException(ApiFailedException ex, HttpServletResponse response) {
        response.setStatus(ex.getHttpStatus().value());
        log.error("ApiFailedException: ", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        log.error("Exception: ", ex);
        return ex.getMessage();
    }
}
