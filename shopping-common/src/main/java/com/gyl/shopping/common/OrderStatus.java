package com.gyl.shopping.common;

import lombok.Data;

public enum OrderStatus {

    ORDER_CANCEL(1,"已取消"),
    ORDER_NOT_PAY(10,"未支付"),
    ORDER_HAS_PAY(20,"已支付"),
    ORDER_HAS_RECEIVER(30,"已发货"),
    ORDER_FINALLY(40,"已完成");

    OrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OrderStatus getStatusByCode(Integer code){
        for (OrderStatus orderStatus:OrderStatus.values()){
            if (orderStatus.getCode().equals(code)){
                return orderStatus;
            }
        }
        throw new MallException(ExceptionEnum.ORDER_STATUS_ERROR);
    }
}
