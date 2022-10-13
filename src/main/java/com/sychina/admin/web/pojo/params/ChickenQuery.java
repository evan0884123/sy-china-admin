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
public class ChickenQuery extends PageQuery {

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

    @ApiModelProperty(value = "0-创建时间, 1-修改时间",required = true)
    @NotNull
    private Integer timeType;
}
