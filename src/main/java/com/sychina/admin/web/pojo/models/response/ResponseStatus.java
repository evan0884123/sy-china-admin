package com.sychina.admin.web.pojo.models.response;

/**
 * 响应常量
 */
public enum ResponseStatus {

    EXISTS("exists", 100),
    SUCCESS("success", 200),
    FAILURE("failure", 500),
    IN_USE("in_use", 300),
    INCORRECT("incorrect", 400);


    public String status;

    public Integer code;

    ResponseStatus(java.lang.String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String status() {
        return status;
    }

    public Integer code() {
        return code;
    }
}
