package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 */
@Data
public class ConfigParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "Icon")
    private String icon;

    @ApiModelProperty(value = "关于")
    private String about;

    @ApiModelProperty(value = "远景")
    private String vision;

    @ApiModelProperty(value = "国债")
    private String project;

    @ApiModelProperty(value = "客服")
    private String customer;

    @ApiModelProperty(value = "企业责任")
    private String dutyOne;

    @ApiModelProperty(value = "企业责任")
    private String dutyTwo;

    @ApiModelProperty(value = "企业视频展示")
    private String video;

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

}
