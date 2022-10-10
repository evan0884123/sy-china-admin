package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private Integer bank;
}
