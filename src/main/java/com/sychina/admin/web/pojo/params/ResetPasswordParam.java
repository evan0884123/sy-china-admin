package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class ResetPasswordParam {

    @ApiModelProperty(value = "id")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
