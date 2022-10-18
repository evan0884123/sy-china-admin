package com.sychina.admin.web.pojo.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class HallTodayStaModel {

    @ApiModelProperty(value = "今日首冲人数")
    private Integer tFirstRecharge;

    @ApiModelProperty(value = "今日充值次数")
    private Integer tRechargeTimes;

    @ApiModelProperty(value = "今日充值人数")
    private Integer tRechargeUserNum;

    @ApiModelProperty(value = "今日充值总额")
    private BigDecimal tTotalRecharge;

    @ApiModelProperty(value = "今日提现笔数")
    private Integer tWithdrawCount;

    @ApiModelProperty(value = "今日提现人数")
    private Integer tWithdrawUsersNum;

    @ApiModelProperty(value = "今日提现总额")
    private BigDecimal tTotalWithdraw;

    @ApiModelProperty(value = "今日待提现金额")
    private BigDecimal tReadyWithdraw;

    @ApiModelProperty(value = "今日已提现金额")
    private BigDecimal tCompleteWithdraw;

    @ApiModelProperty(value = "今日注册用户")
    private Integer tRegisterUserNum;

    @ApiModelProperty(value = "今日登录人数")
    private Integer loginUserNum;
}
