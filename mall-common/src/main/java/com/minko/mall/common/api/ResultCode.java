package com.minko.mall.common.api;

import org.springframework.http.HttpStatus;

public enum ResultCode implements IErrorCode {
    SUCCESS(HttpStatus.OK.value(), "Success"),
    FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ServerError"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "BadRequest"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "NotFound"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "MethodNotAllowed"),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE.value(), "NotAcceptable"),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT.value(), "RequestTimeout"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "UnsupportedMediaType");

    private final long code;
    private final String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
