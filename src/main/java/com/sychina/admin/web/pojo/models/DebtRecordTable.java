package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.DebtRecords;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class DebtRecordTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "国债ID")
    private Long debt;

    @ApiModelProperty(value = "国债名称")
    private String debtName;

    @ApiModelProperty(value = "玩家ID")
    private Long player;

    @ApiModelProperty(value = "玩家姓名")
    private String playerName;

    @ApiModelProperty(value = "投资转化(0-未 1-已)")
    private Integer invest;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public DebtRecordTable(DebtRecords record) {

        this.setId(record.getId())
                .setDebt(record.getDebt())
                .setDebtName(record.getDebtName())
                .setPlayer(record.getPlayer())
                .setPlayerName(record.getPlayerName())
                .setInvest(record.getInvest())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
