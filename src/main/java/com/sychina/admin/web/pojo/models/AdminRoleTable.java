package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.AdminMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description: 角色列表表格元素
 *
 * @author Administrator
 */
@Data
public class AdminRoleTable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "菜单list")
    private List<AdminMenu> adminMenus;
}
