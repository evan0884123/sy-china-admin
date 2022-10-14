package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * @author Administrator
 */
@Data
public class WithdrawApplyParam {

    @ApiModelProperty(value = "id list")
    private List<Long> ids;

    @ApiModelProperty(value = "状态(0-申请 1-操作中 2-通过 3-拒绝)")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
