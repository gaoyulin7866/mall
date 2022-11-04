package com.gyl.shopping.common;

/**
 * @Author: gyl
 * @Description: 用户角色
 */
public enum UserRule {
    NORMAL(1,"普通用户"),
    ADMIN(2,"管理员");

    private Integer code;
    private String desc;

    UserRule(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
}
