package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sychina.admin.common.UUIDGenerator;
import lombok.Data;

/**
 * 后台用户信息
 * @author Administrator
 */
@Data
@TableName("admin_user")
public class User {

    public User() {
        this.id = UUIDGenerator.random();
    }

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    private String loginName;

    private String fullName;

    private String subDepartments;

    private Integer roleId;

    private String mobile;

    private String email;

    private String password;

    private String type;

    public User(User user) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.password = user.getPassword();
    }

}
