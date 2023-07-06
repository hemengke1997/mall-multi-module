package com.minko.mall.common.exception;

public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }
}
