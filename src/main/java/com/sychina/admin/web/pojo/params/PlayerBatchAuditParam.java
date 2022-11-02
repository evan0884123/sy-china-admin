package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class PlayerBatchAuditParam {

    @ApiModelProperty(value = "id list")
    @NotNull
    private List<Long> ids;

    @ApiModelProperty(value = "是否认证经理人(0-未提交 1-审核中 2-已验证 3-审核不通过)")
    private Integer isVerifyManager;

    @ApiModelProperty(value = "状态(0-禁用 1-正常)")
    private Integer status;

}
