package com.gyl.shopping.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MallException extends RuntimeException{
    private Integer code;
    private String message;

    public MallException(ExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }
}
