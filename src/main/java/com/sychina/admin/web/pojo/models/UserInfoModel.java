package com.sychina.admin.web.pojo.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class UserInfoModel {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户姓名")
    private String fullName;

}
