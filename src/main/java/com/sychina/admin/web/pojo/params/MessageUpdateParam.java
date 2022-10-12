package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.Messages;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class MessageUpdateParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private Integer content;

    public Messages convert() {

        Messages messages = new Messages();
        messages.setTitle(this.getTitle())
                .setContent(this.getContent());

        return messages;
    }
}
