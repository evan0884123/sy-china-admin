package com.sychina.admin.web.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Description:角色页面表单
 *
 * @author Administrator
 */
@Data
public class RoleModel {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String menus;

}
