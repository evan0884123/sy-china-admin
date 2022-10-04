package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色
 * @author Administrator
 */
@Data
@TableName(value = "admin_role")
public class Role {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String name;

    private String menus;
}
