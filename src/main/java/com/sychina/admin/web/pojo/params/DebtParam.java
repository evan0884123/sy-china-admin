package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Debts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class DebtParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String numbering;

    @ApiModelProperty(value = "口号")
    private String slogan;

    @ApiModelProperty(value = "项目金额")
    private BigDecimal price;

    @ApiModelProperty(value = "国债年利率")
    private BigDecimal apr;

    @ApiModelProperty(value = "年限")
    private Long term;

    @ApiModelProperty(value = "总计金额")
    private BigDecimal total;

    @ApiModelProperty(value = "状态(0-关闭 1-启用)")
    private Integer status;

    @ApiModelProperty(value = "是否售罄(0-否 1-是)")
    private Integer soldOut;

    public Debts convert(Debts debts) {

        debts.setSlogan(this.getSlogan())
                .setPrice(this.getPrice())
                .setApr(this.getApr())
                .setTerm(this.getTerm())
                .setTotal(this.getTotal())
                .setStatus(this.getStatus())
                .setSoldOut(this.getSoldOut());

        return debts;
    }
}
