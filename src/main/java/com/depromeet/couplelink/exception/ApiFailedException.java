package com.depromeet.couplelink.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiFailedException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ApiFailedException() {
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiFailedException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ApiFailedException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
