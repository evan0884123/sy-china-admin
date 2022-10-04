package com.sychina.admin.web.model;

import lombok.Data;

/**
 * Description: 用户页面表格展示
 */
@Data
public class UserTableModel {

    private String id;

    private String loginName;

    private String fullName;

    private String subDepartments;

    private Integer roleId;

    private String roleName;

    private String mobile;

    private String email;

    private String password;

    private String type;

}
