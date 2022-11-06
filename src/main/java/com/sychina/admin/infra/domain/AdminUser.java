package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台用户信息
 *
 * @author Administrator
 */
@Data
@NoArgsConstructor
@TableName("admin_user")
public class AdminUser {

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

    /**
     * 谷歌验证器的 验证码
     */
    private String googleSecret;

    /**
     * '创建时间'
     */
    @TableField("`create`")
    private Long create;

    /**
     * '修改时间'
     */
    @TableField("`update`")
    private Long update;

    public AdminUser(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.loginName = adminUser.getLoginName();
        this.password = adminUser.getPassword();
    }

}
