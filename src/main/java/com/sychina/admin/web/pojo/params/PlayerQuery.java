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
public class PlayerQuery extends PageQuery {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "VIP等级")
    private Long vIp;

    @ApiModelProperty(value = "层级信息(上上级/上级)")
    private String levelInfo;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号码")
    private String idNumber;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "是否认证经理人(是否认证经理人(0-未提交 1-审核中 2-已验证 3-审核不通过))")
    private Integer isVerifyManager;

    @ApiModelProperty(value = "状态(0-禁用 1-正常)")
    private Integer status;

    @ApiModelProperty(value = "0-创建时间, 1-修改时间, 2-登录时间", required = true)
    @NotNull
    private Integer timeType;
}
