package com.sychina.admin.web.pojo.params;

import com.alibaba.fastjson.JSONObject;
import com.sychina.admin.infra.domain.Projects;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Description:
 */
@Data
public class ProjectParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "国债编号", required = true)
    @NotBlank
    private String debtNumbering;

    @ApiModelProperty(value = "国债名称")
    private String debtName;

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

    public Projects convert() {

        String[] dirs = this.getDir().split(",");
        String[] wdls = this.getWithdrawLc().split(",");
        Assert.isTrue(dirs.length == wdls.length, "项目日利率和项目提现天数的数量必须对应");

        Projects projects = new Projects();
        projects.setName(this.getName())
                .setInvestThreshold(this.getInvestThreshold())
                .setLifeCycle(this.getLifeCycle())
                .setDir("[" + this.getDir() + "]")
                .setFbBalance(this.getFbBalance())
                .setFbWithdraw(this.getFbWithdraw())
                .setWithdrawLc("[" + this.getWithdrawLc() + "]")
                .setWithdrawRate(this.getWithdrawRate())
                .setWithdrawThreshold(this.getWithdrawThreshold())
                .setStatus(this.getStatus());

        return projects;
    }
}
