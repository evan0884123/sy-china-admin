package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 菜单
 *
 * @author Administrator
 */
@Data
@TableName("admin_menu")
public class Menu {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 菜单code
     */
    private String code;

    /**
     * 菜单名称
     */
    private String name;
}
