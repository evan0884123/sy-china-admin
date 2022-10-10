package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Description:
 */
@Data
public class ProjectParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "限购数量(0-不限购 -1-人均限购 >1-限购数量)")
    private Integer limit;

    @ApiModelProperty(value = "类型(0-可以认购 1-不能认购 2-人均认购)")
    private Integer type;

    @ApiModelProperty(value = "售价")
    private BigDecimal price;

    @ApiModelProperty(value = "立返可用余额")
    private BigDecimal fbUseAmount;

    @ApiModelProperty(value = "立返可提金额")
    private BigDecimal fbWithdrawAmount;

    @ApiModelProperty(value = "收益率")
    private BigDecimal rate;

    @ApiModelProperty(value = "产生周期(单位:小时)")
    private Integer produce_cycle;

    @ApiModelProperty(value = "提现周期(单位:小时 0-随记录配置)")
    private Integer withdrawCycle;

    @ApiModelProperty(value = "周期提现次数(0-无限制)")
    private Integer withdrawCycleCount;

    @ApiModelProperty(value = "生命周期(单位:小时 0-无限)")
    private Integer lifeCycle;

    @ApiModelProperty(value = "上级返现")
    private BigDecimal superior;

    @ApiModelProperty(value = "配赠项目ID(0-无配赠)")
    private Integer giftProject;

    @ApiModelProperty(value = "配赠项目配置")
    private String giftConfig;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "背景图片")
    private String background;

    @ApiModelProperty(value = "状态(0-关闭 1-启用)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;
}
