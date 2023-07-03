package com.hh.jushuitan.base;

public enum ResultStatus {
    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    UNAUTHORIZED(401, "未获取到正确的登录令牌"),
    FORBIDDEN(403, "无权限访问"),
    INVALID(9000, "用户名/密码错误"),
    EXPIRED_CAPTCHA(9001, "验证码已过期"),
    INVALID_CAPTCHA(9002, "验证码不正确"),
    EXPIRED_SESSION(9003, "会话已过期！"),
    WITHOUT_LOGIN_USER(9004, "未获取到登录用户！"),

    NOT_FOUND(404, "对象未找到");

    private final Integer code;
    private final String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return SUCCESS.equals(this);
    }
}
