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
     * '限购数量(0-不限购 -1-人均限购 >1-限购数量)'
     */
    private Integer limit;

    /**
     * '类型(0-可以认购 1-不能认购 2-人均认购)'
     */
    private Integer type;

    /**
     * '售价'
     */
    private BigDecimal price;

    /**
     * '立返可用余额'
     */
    private BigDecimal fbUseAmount;

    /**
     * '立返可提金额'
     */
    private BigDecimal fb_withdraw_amount;

    /**
     * '收益率'
     */
    private BigDecimal rate;

    /**
     * '产生周期(单位:小时)'
     */
    private Integer produce_cycle;

    /**
     * '提现周期(单位:小时 0-随记录配置)'
     */
    private Integer withdraw_cycle;

    /**
     * '周期提现次数(0-无限制)'
     */
    private Integer withdraw_cycle_count;

    /**
     * '生命周期(单位:小时 0-无限)'
     */
    private Integer life_cycle;

    /**
     * ''上级返现''
     */
    private BigDecimal superior;

    /**
     * '配赠项目ID(0-无配赠)'
     */
    private Integer gift_project;

    /**
     * '配赠项目配置'
     */
    private String gift_config;

    /**
     * '标题'
     */
    private String title;

    /**
     * '副标题'
     */
    private String sub_title;

    /**
     * '背景图片'
     */
    private String background;

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
