package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class BuyRecordQuery extends PageQuery {

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "项目名")
    private Long projectName;

    @ApiModelProperty(value = "玩家名称")
    private Long account;

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
}
