package com.sychina.admin.web.pojo.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 用户页面表格展示
 */
@Data
public class UserTableModel {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户姓名")
    private String fullName;

    @ApiModelProperty(value = "所属部门")
    private String subDepartments;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "email地址")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户类型")
    private String type;

}
