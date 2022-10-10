package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class BannerParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "栏目banner(0-首页 1-投资 2-咨询)")
    private Integer tab;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态(0-关闭 1-开启)")
    private Integer status;
}
