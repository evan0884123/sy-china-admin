package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sychina.admin.common.UUIDGenerator;
import lombok.Data;

/**
 * 后台用户信息
 *
 * @author Administrator
 */
@Data
@TableName("admin_user")
public class AdminUser {

    public AdminUser() {
        this.id = UUIDGenerator.random();
    }

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String fullName;

    /**
     * 所属部门
     */
    private String subDepartments;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * email地址
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型
     */
    private String type;

    public AdminUser(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.loginName = adminUser.getLoginName();
        this.password = adminUser.getPassword();
    }

}
