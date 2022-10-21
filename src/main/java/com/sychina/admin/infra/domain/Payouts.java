package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("payouts")
public class Payouts {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * '支付方式(0-人工 1-微信 2-支付宝 3-云闪付)'
     */
    private Integer type;

    /**
     * 支付链接
     */
    private String url;

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
