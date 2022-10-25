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
public class AccessLogQuery extends PageQuery {

    @ApiModelProperty(value = "用户名")
    private String adminUserName;

    @ApiModelProperty(value = "0-操作时间",required = true)
    @NotNull
    private Integer timeType;
}
