package com.sychina.admin.web.pojo.params;

import com.sychina.admin.infra.domain.News;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class NewsParam {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "title")
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

    @ApiModelProperty(value = "标签(0-首页 1-资讯)")
    private Integer tab;

    public News convert(News news) {

        news.setTitle(this.getTitle())
                .setThumbnail(this.getThumbnail())
                .setType(this.getType())
                .setContent(this.getContent())
                .setVideoLink(this.getVideoLink())
                .setAuthor(this.getAuthor())
                .setTab(this.getTab());

        return news;
    }
}
