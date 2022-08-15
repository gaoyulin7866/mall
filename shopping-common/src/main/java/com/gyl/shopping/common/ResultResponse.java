package com.gyl.shopping.common;

import lombok.Data;

@Data
public class ResultResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultResponse(){
        this.code = ResultCode.OK.getCode();
        this.message = ResultCode.OK.getMessage();
    }

    public ResultResponse(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ResultResponse(Integer code, String msg){
        this.code = code;
        this.message = msg;
    }

    public ResultResponse<T> put(T data){
        this.data = data;
        return this;
    }

    public static <T>ResultResponse<T> success(){
        return new ResultResponse<T>();
    }

    public static <T>ResultResponse<T> success(ResultCode resultCode){
        return new ResultResponse<T>(resultCode);
    }

    public static <T>ResultResponse<T> success(Integer code, String msg){
        return new ResultResponse<T>(code, msg);
    }

    public static <T>ResultResponse<T> fail(){
        return new ResultResponse<T>(ResultCode.ERROR);
    }

    public static <T>ResultResponse<T> fail(ResultCode resultCode){
        return new ResultResponse<T>(resultCode);
    }

    public static <T>ResultResponse<T> fail(Integer code, String msg){
        return new ResultResponse<T>(code, msg);
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
