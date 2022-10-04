package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Description:
 */
@Data
public class UserParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "id", required = true)
    @NotEmpty
    private String loginName;

    @ApiModelProperty(value = "用户姓名", required = true)
    @NotEmpty
    private String fullName;

    @ApiModelProperty(value = "所属部门")
    @NotEmpty
    private String subDepartments;

    @ApiModelProperty(value = "角色ID")
    @NotNull
    private Integer roleId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "email地址")
    @Email
    private String email;

    @ApiModelProperty(value = "用户类型")
    @NotEmpty
    private String type;

}
