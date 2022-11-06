package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class BankQuery extends PageQuery {

    @ApiModelProperty(value = "玩家账号")
    private String playerName;

    @ApiModelProperty(value = "开户银行")
    private String bank;

    @ApiModelProperty(value = "核实(0-未 1-已)")
    private Integer verified;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间", required = true)
    private Integer timeType;
}
