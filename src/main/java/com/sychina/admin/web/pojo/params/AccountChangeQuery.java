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
public class AccountChangeQuery extends PageQuery {

    @ApiModelProperty(value = "玩家名称")
    private String playerName;

    @ApiModelProperty(value = "变动金额类型(0-可用余额 1-可提现余额 2-推广金额 3-项目收益 4-共享金收益)")
    private BigDecimal amountType;

    @ApiModelProperty(value = "变动类型(0-充值 1-提现 2-项目收益 3-开蛋奖励 4-项目返现可用 5-推广充值返现 6-认购项目 7-注册赠送 8-项目返现可提 9-项目转出共享金 10-共享金转入 11-共享金收益 12-推广注册奖励 13-推广认购奖励)")
    private Integer changeType;

    @ApiModelProperty(value = "关联ID")
    private String connId;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间", required = true)
    @NotNull
    private Integer timeType;
}
