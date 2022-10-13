package com.sychina.admin.web.pojo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sychina.admin.infra.domain.Debts;
import com.sychina.admin.infra.domain.Projects;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 * @author Administrator
 */
@Data
public class DebtParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "口号")
    private String slogan;

    @ApiModelProperty(value = "项目金额")
    private String price;

    @ApiModelProperty(value = "国债年利率")
    private String apr;

    @ApiModelProperty(value = "年限")
    private String term;

    @ApiModelProperty(value = "总计金额")
    private String total;

    @ApiModelProperty(value = "挂载项目")
    private String mount;

    @ApiModelProperty(value = "状态(0-关闭 1-启用)")
    private String status;

    public Debts convert() {

        Debts debts = new Debts();
        debts.setName(this.getName())
                .setSlogan(this.getSlogan())
                .setPrice(this.getPrice())
                .setApr(this.getApr())
                .setTerm(this.getTerm())
                .setTotal(this.getTotal())
                .setMount(this.getMount())
                .setStatus(this.getStatus());

        return debts;
    }
}
