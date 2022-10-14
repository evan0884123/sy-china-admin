package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.Messages;
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
public class MessageTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "玩家账号")
    private String playerName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "已读(0-未读 1-已读)")
    private Integer hadRead;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public MessageTable(Messages record) {

        this.setId(record.getId())
                .setPlayerName(record.getPlayerName())
                .setTitle(record.getTitle())
                .setContent(record.getContent())
                .setContent(record.getContent())
                .setHadRead(record.getHadRead())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
