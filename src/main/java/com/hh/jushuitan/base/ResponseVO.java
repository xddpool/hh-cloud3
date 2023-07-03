package com.hh.jushuitan.base;


public class ResponseVO<T> {
    /** 结果码 */
    private Integer code;
    /** 响应消息 */
    private String message;
    /** 是否成功 */
    private Boolean success;
    /** 状态码 */
    private Integer status;
    /** 时间戳 */
    private Long timestamp;
    /** 处理 */
    private String handler;
    /** 异常信息 */
    private String exception;
    /** 消息体 */
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseVO() {
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseVO(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseVO<T> success() {
        ResponseVO<T> vo = new ResponseVO<>(ResultStatus.SUCCESS);
        vo.setSuccess(true);
        return vo;
    }

    public static <T> ResponseVO<T> success(T data) {
        ResponseVO<T> vo = new ResponseVO<>(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage(), data);
        vo.setSuccess(true);
        return vo;
    }

    public static <T> ResponseVO<T> fail() {
        ResponseVO<T> vo = new ResponseVO<>(ResultStatus.FAIL);
        vo.setSuccess(Boolean.FALSE);
        return vo;
    }

    public static <T> ResponseVO<T> fail(String message) {
        ResponseVO<T> vo = new ResponseVO<>(ResultStatus.FAIL.getCode(), message, null);
        vo.setSuccess(Boolean.FALSE);
        return vo;
    }

    public static <T> ResponseVO<T> fail(ResultStatus status) {
        ResponseVO<T> vo = new ResponseVO<>(status);
        vo.setSuccess(Boolean.FALSE);
        return vo;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
