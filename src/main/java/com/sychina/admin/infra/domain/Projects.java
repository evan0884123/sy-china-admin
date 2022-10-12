package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目信息
 *
 * @author Administrator
 */
@Data
@TableName("projects")
public class Projects {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * '项目名称'
     */
    private String name;

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
     * '项目提现天数'
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
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
