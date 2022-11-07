package com.sychina.admin.infra.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 公司信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("debts")
public class Debts {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JSONField(name = "id")
    private Long id;

    /**
     * '名称'
     */
    @JSONField(name = "name")
    private String name;

    /**
     * '编号
     */
    @JSONField(name = "numbering")
    private String numbering;

    /**
     * '口号'
     */
    @JSONField(name = "slogan")
    private String slogan;


    /**
     * '项目金额'
     */
    @JSONField(name = "price")
    private BigDecimal price;


    /**
     * '国债年利率'
     */
    @JSONField(name = "apr")
    private BigDecimal apr;


    /**
     * '年限'
     */
    @JSONField(name = "term")
    private Long term;

    /**
     * '总计金额'
     */
    @JSONField(name = "total")
    private BigDecimal total;


    /**
     * '挂载项目'
     */
    @JSONField(name = "mount")
    private String mount;


    /**
     * '状态(0-关闭 1-启用)'
     */
    @JSONField(name = "status")
    private Integer status;

    /**
     * 是否售罄(0-否 1-是)
     */
    @JSONField(name = "sold_out")
    private Integer soldOut;

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
