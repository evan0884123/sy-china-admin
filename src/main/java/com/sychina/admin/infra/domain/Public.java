package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * '内容'
     */
    private String content;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
