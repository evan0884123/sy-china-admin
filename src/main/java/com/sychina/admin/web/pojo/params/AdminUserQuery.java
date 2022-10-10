package com.sychina.admin.web.pojo.params;

import com.sychina.admin.web.pojo.params.page.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class AdminUserQuery extends PageQuery {

    @ApiModelProperty(value = "用户名或姓名")
    private String name;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
}
