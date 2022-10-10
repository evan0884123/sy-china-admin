package com.sychina.admin.web.pojo.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
public class BuyRecordTableModel {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "项目名")
    private Long projectName;

    @ApiModelProperty(value = "玩家名称")
    private Long account;

    @ApiModelProperty(value = "关联认购记录ID")
    private Long connRecordId;

    @ApiModelProperty(value = "来源(0-默认来源 1-充值赠送来源 2-限购认购来源)")
    private Integer type;

    @ApiModelProperty(value = "是否收取(0-未收取 1-已经收取)")
    private Integer collect;

    @ApiModelProperty(value = "累计收益")
    private BigDecimal totalProfit;

    @ApiModelProperty(value = "提现门槛")
    private BigDecimal withdrawThreshold;

    @ApiModelProperty(value = "失效时间(0-无限有效)")
    private Integer stopTime;

    @ApiModelProperty(value = "状态(0-过期 1-正常)")
    private Integer status;

    @ApiModelProperty(value = "配置")
    private String config;

    @ApiModelProperty(value = "创建时间")
    private Long create;
}
