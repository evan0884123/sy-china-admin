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
public class EquitiesQuery extends PageQuery {

    @ApiModelProperty(value = "玩家姓名")
    private String playerName;

    @ApiModelProperty(value = "公司名称")
    private String company;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间",required = true)
    @NotNull
    private Integer timeType;
}
