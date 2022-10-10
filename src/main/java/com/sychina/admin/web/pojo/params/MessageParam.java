package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class MessageParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "玩家账号")
    private String account;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private Integer content;

    @ApiModelProperty(value = "已读(0-未读 1-已读)")
    private Integer hadRead;
}
