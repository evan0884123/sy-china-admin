package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Config;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class ConfigTable {

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

    @ApiModelProperty(value = "共享金开关(0-关 1-开)")
    private Integer smSwitch;

    @ApiModelProperty(value = "下载二维码图片地址")
    private String downloadQrCodeImg;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public ConfigTable(Config record) {

        this.setId(record.getId())
                .setIcon(record.getIcon())
                .setAbout(record.getAbout())
                .setVision(record.getVision())
                .setProject(record.getProject())
                .setProject(record.getProject())
                .setCustomer(record.getCustomer())
                .setDutyOne(record.getDutyOne())
                .setDutyTwo(record.getDutyTwo())
                .setVideo(record.getVideo())
                .setSmScale(record.getSmScale())
                .setSmYearRate(record.getSmYearRate())
                .setSmWithdrawThreshold(record.getSmWithdrawThreshold())
                .setSmWithdrawLifeCycle(record.getSmWithdrawLifeCycle())
                .setSmSwitch(record.getSmSwitch())
                .setDownloadQrCodeImg(record.getDownloadQrCodeImg())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
