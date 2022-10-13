package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Equities;
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
public class EquitiesTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家ID")
    private Long player;

    @ApiModelProperty(value = "玩家姓名")
    private String playerName;

    @ApiModelProperty(value = "数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "公司名称")
    private String company;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public EquitiesTable(Equities record) {

        this.setId(record.getId())
                .setPlayer(record.getPlayer())
                .setPlayerName(record.getPlayerName())
                .setAmount(record.getAmount())
                .setCompany(record.getCompany())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
