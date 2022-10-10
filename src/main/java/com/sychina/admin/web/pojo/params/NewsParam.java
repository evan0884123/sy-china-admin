package com.sychina.admin.web.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * @author Administrator
 */
@Data
public class NewsParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "id")
    private String title;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "类型(0-纯文字 1-图文 2-视频)")
    private Integer type;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "视频链接")
    private String videoLink;

    @ApiModelProperty(value = "出处")
    private String author;

}
