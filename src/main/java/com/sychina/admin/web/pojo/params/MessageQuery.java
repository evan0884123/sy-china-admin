package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class MessageQuery extends PageQuery {

    @ApiModelProperty(value = "玩家账号")
    private String playerName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "已读(0-未读 1-已读)")
    private Integer hadRead;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间",required = true)
    private Integer timeType;
}
