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
    @TableId(value = "id", type = IdType.AUTO)
    @JSONField(name = "id")
    private Long id;

    /**
     * 'Icon'
     */
    @JSONField(name = "icon")
    private String icon;

    /**
     * '关于'
     */
    @JSONField(name = "about")
    private String about;

    /**
     * '远景'
     */
    @JSONField(name = "vision")
    private String vision;

    /**
     * '国债'
     */
    @JSONField(name = "project")
    private String project;

    /**
     * '客服'
     */
    @JSONField(name = "customer")
    private String customer;

    /**
     * '企业责任'
     */
    @JSONField(name = "duty_one")
    private String dutyOne;

    /**
     * '企业责任'
     */
    @JSONField(name = "duty_two")
    private String dutyTwo;

    /**
     * '企业视频展示'
     */
    @JSONField(name = "video")
    private String video;

    /**
     * 共享金补贴比例
     */
    @JSONField(name = "sm_scale")
    private BigDecimal smScale;

    /**
     * 共享金年利率
     */
    @JSONField(name = "sm_year_rate")
    private BigDecimal smYearRate;

    /**
     * 共享金提现门槛
     */
    @JSONField(name = "sm_withdraw_threshold")
    private BigDecimal smWithdrawThreshold;

    /**
     * 共享金提现生命周期(单位:月)
     */
    @JSONField(name = "sm_withdraw_life_cycle")
    private Long smWithdrawLifeCycle;

    /**
     * 共享金提现比例
     */
    @JSONField(name = "sm_withdraw_rate")
    private BigDecimal smWithdrawRate;

    /**
     * 共享金开关(0-关 1-开)
     */
    @JSONField(name = "sm_switch")
    private Integer smSwitch;

    /**
     * 下载二维码图片地址
     */
    @JSONField(name = "download_qr_code_img")
    private String downloadQrCodeImg;

    /**
     * 推广邀请注册奖励规则
     */
    @JSONField(name = "promote_register_rule")
    private String promoteRegisterRule;

    /**
     * 推广邀请认购奖励规则
     */
    @JSONField(name = "promote_buy_rule")
    private String promoteBuyRule;

    /**
     * 开盘活动开始时间
     */
    @JSONField(name = "ce_start_time")
    private Long ceStartTime;

    /**
     * 开盘活动结束时间
     */
    @JSONField(name = "ce_stop_time")
    private Long ceStopTime;

    /**
     * 登录赠送开始时间
     */
    @JSONField(name = "lg_start_time")
    private Long lgStartTime;

    /**
     * 登录赠送结束时间
     */
    @JSONField(name = "lg_stop_time")
    private Long lgStopTime;

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
