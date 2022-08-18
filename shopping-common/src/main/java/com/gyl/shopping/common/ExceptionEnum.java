package com.gyl.shopping.common;

public enum ExceptionEnum {
    USERNAME_NOT_NULL(10000, "用户名不能为空"),
    PASSWORD_NOT_NULL(10001, "密码不能为空"),
    EMAIL_NOT_NULL(10002, "邮箱不能为空"),
    EMAILCODE_NOT_NULL(10003, "验证码不能为空"),
    EMAIL_NOT_CORRECT(10004, "邮箱格式不正确"),
    EMAIL_SEND_ERROR(10005, "验证码发送失败"),
    GET_CODE_AGAIN(10006, "请重新获取验证码"),
    CODE_ERROR(10007, "验证码不正确"),
    NOT_USER(10008, "用户不存在"),
    USER_HAS_EXIST(10009, "用户已存在"),
    PASSWORD_ERROR(10010, "密码不正确"),
    NEED_LOGIN(10011, "用户未登录"),
    UPDATE_ERROR(10012, "更新失败！"),
    PARAMS_ERROR(10013, "请求参数不正确！"),
    ADD_CART_ERROR(10014, "添加购物车失败！"),
    NO_CART_ERROR(10015, "暂未添加购物车！"),
    DELETE_ERROR(10016, "删除失败！"),
    NOT_PRODUCT(10017, "商品不存在！"),
    EMPTY_CART(10018, "请先添加商品到购物车！"),
    ORDER_ERROR(10019, "订单创建失败！"),
    ORDER_STATUS_ERROR(10020, "订单状态不正确！"),
    ORDER_NOT_FOUND(10021, "订单不存在！"),
    CANCEL_ORDER_ERROR(10022, "订单取消失败！"),
    CREATE_IMG_ERROR(10023, "图片创建失败！"),
    PAY_ERROR(10024, "支付失败！"),
    CATEGORY_EXIST(10025, "该分类名已存在！"),
    NEW_ERROR(10026, "新增失败！"),
    CATEGORY_NOT_EXIST(10027, "分类不存在！"),
    ADD_PRODUCT_ERROR(10028, "商品添加失败！"),
    TOKEN_ERROR(10029, "Token过期！"),
    LOGOUT(10030, "已退出登陆！");

    private Integer code;
    private String message;

    ExceptionEnum() {
    }

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
