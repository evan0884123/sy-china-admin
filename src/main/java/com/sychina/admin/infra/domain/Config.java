package com.sychina.admin.infra.domain;

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
    private Long id;

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
     * 共享金提现比例
     */
    private BigDecimal smWithdrawRate;

    /**
     * 共享金开关(0-关 1-开)
     */
    private Integer smSwitch;

    /**
     * 下载二维码图片地址
     */
    private String downloadQrCodeImg;

    /**
     * 推广邀请注册奖励规则
     */
    private String promoteRegisterRule;

    /**
     * 推广邀请认购奖励规则
     */
    private String promoteBuyRule;

    /**
     * 开盘活动开始时间
     */
    private Long ceStartTime;

    /**
     * 开盘活动结束时间
     */
    private Long ceStopTime;

    /**
     * 登录赠送开始时间
     */
    private Long lgStartTime;

    /**
     * 登录赠送结束时间
     */
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
