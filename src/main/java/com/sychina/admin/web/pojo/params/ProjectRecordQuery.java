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
public class ProjectRecordQuery extends PageQuery {

    @ApiModelProperty(value = "玩家昵称")
    private String playerName;

    @ApiModelProperty(value = "项目昵称")
    private String projectName;

    @ApiModelProperty(value = "国债编号")
    private String debtNumbering;

    @ApiModelProperty(value = "状态(0-项目投资阶段 1-共享金阶段)")
    private Integer status;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间",required = true)
    @NotNull
    private Integer timeType;
}
