package com.sychina.admin.infra.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 公共信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("public")
public class Public {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JSONField(name = "id")
    private String id;

    /**
     * '内容'
     */
    @JSONField(name = "content")
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
