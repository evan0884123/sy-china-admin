package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.AdminUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Description: 用户页面表格展示
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class AdminUserTable {

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

    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "菜单list")
    private List<AdminMenuModel> adminMenus;

    public AdminUserTable(AdminUser adminUser) {

        this.setId(adminUser.getId())
                .setLoginName(adminUser.getLoginName())
                .setFullName(adminUser.getFullName())
                .setSubDepartments(adminUser.getSubDepartments())
                .setRoleId(adminUser.getRoleId())
                .setMobile(adminUser.getMobile())
                .setEmail(adminUser.getEmail())
                .setType(adminUser.getType());
    }
}
