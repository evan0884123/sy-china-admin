package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Chickens;
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
public class ChickenTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家ID")
    private Long player;

    @ApiModelProperty(value = "玩家姓名")
    private String playerName;

    @ApiModelProperty(value = "来源类型(0-注册即赠送 1-首充1000送 2-认购)")
    private Integer type;

    @ApiModelProperty(value = "蛋(0-无 1-有)")
    private Integer egg;

    @ApiModelProperty(value = "上次开启时间")
    private Long lastOpenTime;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public ChickenTable(Chickens record) {

        this.setId(record.getId())
                .setPlayer(record.getPlayer())
                .setPlayerName(record.getPlayerName())
                .setType(record.getType())
                .setEgg(record.getEgg())
                .setLastOpenTime(record.getLastOpenTime())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }

}
