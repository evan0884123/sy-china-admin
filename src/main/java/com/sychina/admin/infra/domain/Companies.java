package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 公司信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("companies")
public class Companies {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * '公司名称'
     */
    private String name;

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
