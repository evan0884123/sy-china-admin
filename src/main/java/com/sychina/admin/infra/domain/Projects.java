package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 项目信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("projects")
public class Projects {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * '项目名称'
     */
    private String name;

    /**
     * 国债编号
     */
    private String debtNumbering;

    /**
     * 国债名称
     */
    private String debtName;

    /**
     * '最低准入金额'
     */
    private BigDecimal investThreshold;

    /**
     * '项目生命周期'
     */
    private Integer lifeCycle;

    /**
     * '项目日利率'
     */
    private String dir;

    /**
     * '返现可用认购'
     */
    private BigDecimal fbBalance;

    /**
     * '可提返现'
     */
    private BigDecimal fbWithdraw;

    /**
     * '项目提现天数'  和项目日利率（dir） 一一对应，比如：
     * dir -> [0.1, 0.2, 0.3]
     * withdrawLc -> [1, 3, 5]
     */
    private String withdrawLc;

    /**
     * '可提现周期利率'
     */
    private BigDecimal withdrawRate;

    /**
     * '提现门槛'
     */
    private BigDecimal withdrawThreshold;

    /**
     * '状态(0-关闭 1-启用)'
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
