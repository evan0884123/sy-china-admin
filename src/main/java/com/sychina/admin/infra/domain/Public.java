package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 公共信息
 *
 * @author Administrator
 */
@Data
@TableName("public")
public class Public {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * '内容'
     */
    private String content;

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
