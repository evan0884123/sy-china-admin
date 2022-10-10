package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class AdminUserProfileQuery {

    @ApiModelProperty(value = "用户名或姓名")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String loginName;

}
