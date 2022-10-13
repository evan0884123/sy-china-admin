package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.News;
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
public class NewsTable {

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

    @ApiModelProperty(value = "创建时间")
    private Long create;

    @ApiModelProperty(value = "修改时间")
    private Long update;

    public NewsTable(News record) {

        this.setId(record.getId())
                .setTitle(record.getTitle())
                .setThumbnail(record.getThumbnail())
                .setType(record.getType())
                .setContent(record.getContent())
                .setVideoLink(record.getVideoLink())
                .setAuthor(record.getAuthor())
                .setCreate(record.getCreate())
                .setUpdate(record.getUpdate());
    }
}
