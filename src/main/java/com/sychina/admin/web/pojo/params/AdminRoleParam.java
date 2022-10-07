package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Description:角色页面表单
 *
 * @author Administrator
 */
@Data
public class AdminRoleParam {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "菜单ID", required = true)
    @NotEmpty
    private String menuIds;

}
