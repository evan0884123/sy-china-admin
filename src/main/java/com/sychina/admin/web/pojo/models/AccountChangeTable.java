package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.AccountChanges;
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
public class AccountChangeTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家ID")
    private Long player;

    @ApiModelProperty(value = "玩家名称")
    private String playerName;

    @ApiModelProperty(value = "变动金额类型(0-可用余额 1-可提现余额 2-推广金额 3-项目收益)")
    private Integer amountType;

    @ApiModelProperty(value = "变动前金额")
    private BigDecimal bcBalance;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "变动后金额")
    private BigDecimal acBalance;

    @ApiModelProperty(value = "变动类型(0-充值 1-提现 2-项目收益 3-鸡蛋收益 4-项目返现 5-推广充值返现)")
    private Integer changeType;

    @ApiModelProperty(value = "变动说明")
    private String changeDescribe;

    @ApiModelProperty(value = "关联ID")
    private String connId;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public AccountChangeTable(AccountChanges record) {

        this.setId(record.getId())
                .setPlayer(record.getPlayer())
                .setPlayerName(record.getPlayerName())
                .setAmountType(record.getAmountType())
                .setBcBalance(record.getBcBalance())
                .setAmount(record.getAmount())
                .setAcBalance(record.getAcBalance())
                .setChangeType(record.getChangeType())
                .setChangeDescribe(record.getChangeDescribe())
                .setConnId(record.getConnId())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
