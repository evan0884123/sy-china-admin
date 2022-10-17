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
public class ProjectQuery extends PageQuery {

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "国债名称")
    private String debtName;

    @ApiModelProperty(value = "国债编号")
    private String debtNumbering;

    @ApiModelProperty(value = "状态(0-关闭 1-启用)")
    private Integer status;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间", required = true)
    @NotNull
    private Integer timeType;
}
