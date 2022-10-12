package com.sychina.admin.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 配置信息
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@TableName("config")
public class Config {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 'Icon'
     */
    private String icon;

    /**
     * '关于'
     */
    private String about;

    /**
     * '远景'
     */
    private String vision;

    /**
     * '国债'
     */
    private String project;

    /**
     * '客服'
     */
    private String customer;

    /**
     * '企业责任'
     */
    private String dutyOne;

    /**
     * '企业责任'
     */
    private String dutyTwo;

    /**
     * '企业视频展示'
     */
    private String video;

    /**
     * 共享金补贴比例
     */
    private String smScale;

    /**
     * 共享金年利率
     */
    private String smYearRate;

    /**
     * 共享金提现门槛
     */
    private String smWithdrawThreshold;

    /**
     * 共享金提现生命周期(单位:月)
     */
    private String smWithdrawLifeCycle;

    /**
     * '创建时间'
     */
    private Long create;

    /**
     * '修改时间'
     */
    private Long update;
}
