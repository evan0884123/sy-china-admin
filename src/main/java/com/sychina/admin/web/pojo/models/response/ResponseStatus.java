package com.sychina.admin.web.pojo.models.response;

/**
 * 响应常量
 */
public enum ResponseStatus {

    UNAUTHORIZED("unauthorized", 401),
    UNBIND_GOOGLE("unbind_google", 402),
    BIND_GOOGLE("bind_google", 403),
    GOOGLE_CODE_ERROR("google_code_error", 406),
    EXISTS("exists", 100),
    SUCCESS("success", 200),
    FAILURE("failure", 500),
    IN_USE("in_use", 300),
    INCORRECT("incorrect", 400),
    SYSTEM_ERROR("system_error", 600);


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
