package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description: 角色列表表格元素
 * @author Administrator
 */
@Data
public class RoleTableModel {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "菜单list")
    private List<Menu> menus;
}
