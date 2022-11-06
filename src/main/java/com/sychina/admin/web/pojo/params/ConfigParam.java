package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Config;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class ConfigParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Icon")
    private String icon;

    @ApiModelProperty(value = "关于")
    private String about;

    @ApiModelProperty(value = "远景")
    private String vision;

    @ApiModelProperty(value = "国债")
    private String project;

    @ApiModelProperty(value = "客服")
    private String customer;

    @ApiModelProperty(value = "企业责任")
    private String dutyOne;

    @ApiModelProperty(value = "企业责任")
    private String dutyTwo;

    @ApiModelProperty(value = "企业视频展示")
    private String video;

    @ApiModelProperty(value = "共享金补贴比例")
    private String smScale;

    @ApiModelProperty(value = "共享金年利率")
    private String smYearRate;

    @ApiModelProperty(value = "共享金提现门槛")
    private String smWithdrawThreshold;

    @ApiModelProperty(value = "共享金提现生命周期(单位:月)")
    private String smWithdrawLifeCycle;

    @ApiModelProperty(value = "共享金提现比例")
    private BigDecimal smWithdrawRate;

    @ApiModelProperty(value = "共享金开关(0-关 1-开)")
    private Integer smSwitch;

    @ApiModelProperty(value = "下载二维码图片地址")
    private String downloadQrCodeImg;

    @ApiModelProperty(value = "推广邀请注册奖励规则")
    private String promoteRegisterRule;

    @ApiModelProperty(value = "推广邀请认购奖励规则")
    private String promoteBuyRule;

    @ApiModelProperty(value = "开盘活动开始时间")
    private Long ceStartTime;

    @ApiModelProperty(value = "开盘活动结束时间")
    private Long ceStopTime;

    @ApiModelProperty(value = "登录赠送开始时间")
    private Long lgStartTime;

    @ApiModelProperty(value = "登录赠送结束时间")
    private Long lgStopTime;

    public Config convert() {

        Config config = new Config();
        config.setId(this.getId())
                .setIcon(this.getIcon())
                .setAbout(this.getAbout())
                .setVision(this.getVision())
                .setProject(this.getProject())
                .setCustomer(this.getCustomer())
                .setDutyOne(this.getDutyOne())
                .setDutyTwo(this.getDutyTwo())
                .setVideo(this.getVideo())
                .setSmScale(this.getSmScale())
                .setSmYearRate(this.getSmYearRate())
                .setSmWithdrawThreshold(this.getSmWithdrawThreshold())
                .setSmWithdrawLifeCycle(this.getSmWithdrawLifeCycle())
                .setSmWithdrawRate(this.getSmWithdrawRate())
                .setSmSwitch(this.getSmSwitch())
                .setDownloadQrCodeImg(this.getDownloadQrCodeImg())
                .setPromoteRegisterRule(this.getPromoteRegisterRule())
                .setPromoteBuyRule(this.getPromoteBuyRule())
                .setCeStartTime(this.getCeStartTime())
                .setCeStopTime(this.getCeStopTime())
                .setLgStartTime(this.getLgStartTime())
                .setLgStopTime(this.getLgStopTime());

        return config;
    }
}
