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

    @ApiModelProperty(value = "变动金额类型(0-可用余额 1-可提现余额 2-推广金额 3-项目收益)")
    private BigDecimal amountType;

    @ApiModelProperty(value = "变动类型(0-充值 1-提现 2-项目收益 3-鸡蛋收益 4-项目返现 5-推广充值返现)")
    private Integer changeType;

    @ApiModelProperty(value = "关联ID")
    private String connId;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间", required = true)
    @NotNull
    private Integer timeType;
}
