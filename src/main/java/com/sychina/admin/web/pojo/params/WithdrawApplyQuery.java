package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class WithdrawApplyQuery extends PageQuery {

    @ApiModelProperty(value = "玩家名称")
    private String playerName;

    @ApiModelProperty(value = "类型(0-充值 1-提现)")
    private Integer type;

    @ApiModelProperty(value = "充值支付方式(0-人工客服 1-微信 2-支付宝 3-云闪付 4-注册赠送 5-认购返可用 6-认购返可提现)")
    private Integer chargeChannel;

    @ApiModelProperty(value = "提现类型(0-收益提现 1-推广金提现 2-返现金额提现)")
    private Integer wdType;

    @ApiModelProperty(value = "状态(0-申请 1-操作中 2-通过 3-拒绝)")
    private Integer status;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间",required = true)
    @NotNull
    private Integer timeType;
}
