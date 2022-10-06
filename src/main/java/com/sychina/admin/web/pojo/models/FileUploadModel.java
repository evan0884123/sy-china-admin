package com.sychina.admin.web.pojo.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class FileUploadModel {

    @ApiModelProperty(value = "文件唯一标识")
    private String uid;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "状态有：uploading done error removed")
    private String status;

    @ApiModelProperty(value = "服务端响应内容，如：'{\"status\": \"success\"}'")
    private String response;

}
