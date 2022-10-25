package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.ActionLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class AccessLogTable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "操作人员id")
    private String userId;

    @ApiModelProperty(value = "操作人员账号")
    private String adminUserName;

    @ApiModelProperty(value = "操作方法")
    private String method;

    @ApiModelProperty(value = "操作方法 描述")
    private String methodDes;

    @ApiModelProperty(value = "操作参数")
    private String parameters;

    @ApiModelProperty(value = "操作结果")
    private Integer result;

    @ApiModelProperty(value = "返回值")
    private String returnValue;

    @ApiModelProperty(value = "操作时间")
    private String actionTime;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public AccessLogTable(ActionLog record) {

        this.setId(record.getId())
                .setUserId(record.getUserId())
                .setAdminUserName(record.getAdminUserName())
                .setMethod(record.getMethod())
                .setMethodDes(record.getMethodDes())
                .setParameters(record.getParameters())
                .setResult(record.getResult())
                .setReturnValue(record.getReturnValue())
                .setActionTime(record.getActionTime())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
