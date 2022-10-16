package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Messages;
import com.sychina.admin.utils.LocalDateTimeHelper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class MessageAddParam {

    @ApiModelProperty(value = "玩家账号list ")
    @NotNull
    private List<String> playerNames;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    public Messages convert() {

        Messages messages = new Messages();
        messages.setTitle(this.getTitle())
                .setContent(this.getContent())
                .setHadRead(0)
                .setCreate(LocalDateTimeHelper.toLong(LocalDateTime.now()));

        return messages;
    }
}
