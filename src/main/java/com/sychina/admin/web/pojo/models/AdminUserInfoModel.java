package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.AdminUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class AdminUserInfoModel {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "用户姓名")
    private String fullName;

    @ApiModelProperty(value = "菜单list")
    private List<AdminMenuModel> adminMenus;

    public AdminUserInfoModel(AdminUser record) {

        this.setId(record.getId())
                .setLoginName(record.getLoginName())
                .setFullName(record.getFullName());
    }
}
