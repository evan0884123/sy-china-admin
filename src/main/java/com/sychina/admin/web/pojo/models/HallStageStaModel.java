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
public class HallStageStaModel {

    @ApiModelProperty(value = "总充值金额")
    private BigDecimal totalRechargeAmount;

    @ApiModelProperty(value = "总充值人数")
    private Integer totalRechargeUserNum;

    @ApiModelProperty(value = "总提现金额")
    private BigDecimal totalWithdrawalAmount;

    @ApiModelProperty(value = "总提现笔数")
    private Integer totalWithdrawalCount;

    @ApiModelProperty(value = "总提现人数")
    private Integer totalWithdrawalUserNum;

    @ApiModelProperty(value = "总注册人数")
    private Integer totalRegisterUserNum;

    @ApiModelProperty(value = "累计登录人数")
    private Integer totalLoginUserNum;
}
