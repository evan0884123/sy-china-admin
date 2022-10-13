package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * banner 信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("banners")
public class Banners {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * ''栏目banner(0-首页 1-投资 2-咨询)''
     */
    private Integer tab;

    /**
     * '图片'
     */
    private String img;

    /**
     * '内容'
     */
    private String content;

    /**
     * '状态(0-关闭 1-开启)'
     */
    private Integer status;

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
}
