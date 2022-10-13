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
public class ProjectQuery extends PageQuery {

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "最低准入金额")
    private BigDecimal investThreshold;

    @ApiModelProperty(value = "项目生命周期")
    private Integer lifeCycle;

    @ApiModelProperty(value = "项目日利率")
    private String dir;

    @ApiModelProperty(value = "返现可用认购")
    private BigDecimal fbBalance;

    @ApiModelProperty(value = "可提返现")
    private BigDecimal fbWithdraw;

    @ApiModelProperty(value = "项目提现天数")
    private String withdrawLc;

    @ApiModelProperty(value = "可提现周期利率")
    private BigDecimal withdrawRate;

    @ApiModelProperty(value = "提现门槛")
    private BigDecimal withdrawThreshold;

    @ApiModelProperty(value = "状态(0-关闭 1-启用)")
    private Integer status;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间")
    @NotNull
    private Integer timeType;
}
