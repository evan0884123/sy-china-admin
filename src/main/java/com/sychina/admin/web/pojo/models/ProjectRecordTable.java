package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.ProjectRecords;
import com.sychina.admin.infra.domain.Projects;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class ProjectRecordTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家ID")
    private Long player;

    @ApiModelProperty(value = "玩家昵称")
    private String playerName;

    @ApiModelProperty(value = "项目ID")
    private Long project;

    @ApiModelProperty(value = "项目昵称")
    private String projectName;

    @ApiModelProperty(value = "投资金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "项目日利率")
    private BigDecimal dir;

    @ApiModelProperty(value = "可提现日期")
    private Long withdrawTime;

    @ApiModelProperty(value = "累计收益")
    private BigDecimal grandProfit;

    @ApiModelProperty(value = "可提现周期利率")
    private BigDecimal withdrawRate;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawAmount;

    @ApiModelProperty(value = "提现门槛")
    private BigDecimal withdrawThreshold;

    @ApiModelProperty(value = "项目生命周期")
    private Integer projectLifeCycle;

    @ApiModelProperty(value = "项目剩余天数")
    private Integer projectLeft;

    @ApiModelProperty(value = "项目总收益")
    private BigDecimal totalProfit;

    @ApiModelProperty(value = "状态(0-项目投资阶段 1-共享金阶段)")
    private Integer status;

    @ApiModelProperty(value = "共享总金额")
    private BigDecimal smAmount;

    @ApiModelProperty(value = "共享年利率")
    private BigDecimal smYearRate;

    @ApiModelProperty(value = "共享金累计收益")
    private BigDecimal smGrandProfit;

    @ApiModelProperty(value = "共享金累计提现")
    private BigDecimal smGrandWithdraw;

    @ApiModelProperty(value = "共享金提现门槛")
    private BigDecimal smWithdrawThreshold;

    @ApiModelProperty(value = "共享金上次提现时间")
    private Long smLatestWithdraw;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public ProjectRecordTable(ProjectRecords record){

        this.setId(record.getId())
                .setPlayer(record.getPlayer())
                .setPlayerName(record.getPlayerName())
                .setProject(record.getProject())
                .setProjectName(record.getProjectName())
                .setAmount(record.getAmount())
                .setDir(record.getDir())
                .setWithdrawTime(record.getWithdrawTime())
                .setGrandProfit(record.getGrandProfit())
                .setWithdrawRate(record.getWithdrawRate())
                .setWithdrawAmount(record.getWithdrawAmount())
                .setWithdrawThreshold(record.getWithdrawThreshold())
                .setProjectLifeCycle(record.getProjectLifeCycle())
                .setProjectLeft(record.getProjectLeft())
                .setTotalProfit(record.getTotalProfit())
                .setStatus(record.getStatus())
                .setSmAmount(record.getSmAmount())
                .setSmYearRate(record.getSmYearRate())
                .setSmGrandProfit(record.getSmGrandProfit())
                .setSmGrandWithdraw(record.getSmGrandWithdraw())
                .setSmWithdrawThreshold(record.getSmWithdrawThreshold())
                .setSmLatestWithdraw(record.getSmLatestWithdraw())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
