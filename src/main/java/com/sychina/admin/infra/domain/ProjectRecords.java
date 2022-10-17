package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
@TableName("project_records")
public class ProjectRecords {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * '玩家ID'
     */
    private Long player;

    /**
     * '玩家昵称'
     */
    private String playerName;

    /**
     * '项目ID'
     */
    private Long project;

    /**
     * '项目昵称'
     */
    private String projectName;

    /**
     * '投资金额'
     */
    private BigDecimal amount;

    /**
     * '项目日利率'
     */
    private BigDecimal dir;

    /**
     * '可提现日期'
     */
    private Long withdrawTime;

    /**
     * '累计收益'
     */
    private BigDecimal grandProfit;

    /**
     * '可提现周期利率'
     */
    private BigDecimal withdrawRate;

    /**
     * '提现金额'
     */
    private BigDecimal withdrawAmount;

    /**
     * '提现门槛'
     */
    private BigDecimal withdrawThreshold;

    /**
     * '项目生命周期'
     */
    private Integer projectLifeCycle;

    /**
     * '项目剩余天数'
     */
    private Integer projectLeft;

    /**
     * '项目总收益'
     */
    private BigDecimal totalProfit;

    /**
     * '状态(0-项目投资阶段 1-共享金阶段)'
     */
    private Integer status;

    /**
     * '共享总金额'
     */
    private BigDecimal smAmount;

    /**
     * '共享年利率'
     */
    private BigDecimal smYearRate;

    /**
     * '共享金累计收益'
     */
    private BigDecimal smGrandProfit;

    /**
     * '共享金累计提现'
     */
    private BigDecimal smGrandWithdraw;

    /**
     * '共享金提现门槛'
     */
    private BigDecimal smWithdrawThreshold;

    /**
     * '共享金上次提现时间'
     */
    private Long smLatestWithdraw;

    /**
     * '国债编号'
     */
    private String debtNumbering;

    /**
     * '转换共享金阶段时间'
     */
    private Long smTransferTime;

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
