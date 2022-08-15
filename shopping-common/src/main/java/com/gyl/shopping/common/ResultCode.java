package com.gyl.shopping.common;

public enum ResultCode {
    OK(200, "success"),
    ERROR(0, "error");

    private final Integer code;
    private final String message;

     ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
